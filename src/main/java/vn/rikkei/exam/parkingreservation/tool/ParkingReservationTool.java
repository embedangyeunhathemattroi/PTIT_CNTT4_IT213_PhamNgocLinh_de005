package vn.rikkei.exam.parkingreservation.tool;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import vn.rikkei.exam.parkingreservation.dto.result.CreateParkingSpotRequestResult;
import vn.rikkei.exam.parkingreservation.dto.result.ParkingSpotAvailabilityResult;
import vn.rikkei.exam.parkingreservation.exception.BadRequestException;
import vn.rikkei.exam.parkingreservation.service.ParkingReservationService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
@RequiredArgsConstructor
public class ParkingReservationTool {

    private final ParkingReservationService parkingReservationService;
    private final ToolExecutionTracker toolExecutionTracker;

    @Tool(
            name = "getParkingAvailability",
            description = """
                  Kiểm tra dữ liệu thực  về tình trạng chỗ đậu xe còn khả dụng theo loại chỗ và khoảng ngày.
                   Bắt buộc dùng tool này khi người dùng hỏi chỗ đậu xe còn trống/khả dụng.
                  Không được tự suy đoán tình trạng chỗ đậu xe từ tài liệu RAG hoặc từ kiến thức của LLM.
                startDate và endDate dùng định dạng ISO yyyy-MM-dd và startDate phải <= endDate.

                    """
    )
    public ParkingSpotAvailabilityResult getParkingSpotAvailability(
            @ToolParam(description = "Loại chỗ: STANDARD/STD hoặc PREMIUM/PRM") String resourceType,
            @ToolParam(description = "Ngày bắt đầu theo yyyy-MM-dd") String startDate,
            @ToolParam(description = "Ngày kết thúc theo yyyy-MM-dd") String endDate) {
        toolExecutionTracker.record("getParkingAvailability");
        return parkingReservationService.getParkingSpotAvailability(
                resourceType,
                parseDate(startDate, "startDate"),
                parseDate(endDate, "endDate")
        );
    }

    @Tool(
            name = "createParkingReservationRequest",
            description = """
                    Tạo yêu cầu đặt chỗ đỗ xe bằng Java Service và lưu vào database với trạng thái PENDING.
                    Bắt buộc dùng tool này khi người dùng muốn tạo/đặt chỗ đỗ xe.
                    Tool tự kiểm tra user tồn tại, startDate <= endDate, thời lượng tối đa 14 ngày,
                    số người hợp lệ, PREMIUM tối thiểu 2 người, purpose dài 10-200 ký tự và availability thực tế.
                    Không được tự tạo requestId hoặc tự tuyên bố đã đặt chỗ nếu tool chưa thực thi thành công.
                    """
    )
    public CreateParkingSpotRequestResult createParkingSpotRequest(
            @ToolParam(description = "Mã user có thật trong database, ví dụ USR-001") String userId,
            @ToolParam(description = "Loại chỗ: STANDARD/STD hoặc PREMIUM/PRM") String resourceType,
            @ToolParam(description = "Ngày bắt đầu theo yyyy-MM-dd") String startDate,
            @ToolParam(description = "Ngày kết thúc theo yyyy-MM-dd") String endDate,
            @ToolParam(description = "Số người sử dụng chỗ đỗ xe") Integer participantCount,
            @ToolParam(description = "Mục đích đặt chỗ, từ 10 đến 200 ký tự") String purpose) {
        toolExecutionTracker.record("createParkingReservationRequest");
        return parkingReservationService.createParkingSpotRequest(
                userId,
                resourceType,
                parseDate(startDate, "startDate"),
                parseDate(endDate, "endDate"),
                participantCount,
                purpose
        );
    }

    private LocalDate parseDate(String value, String fieldName) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException | NullPointerException ex) {
            throw new BadRequestException(fieldName + " phải theo định dạng yyyy-MM-dd");
        }
    }
}
