package team.jeonghokim.daedongyeojido.domain.smshistory.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.SmsHistory;
import team.jeonghokim.daedongyeojido.domain.smshistory.domain.repository.SmsHistoryRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DownloadSmsHistoryExcelService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final SmsHistoryRepository smsHistoryRepository;

    @Transactional(readOnly = true)
    public byte[] execute() {
        List<SmsHistory> smsHistories = smsHistoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            XSSFSheet sheet = workbook.createSheet("sms_histories");

            writeHeader(sheet);
            writeRows(sheet, smsHistories);
            autoSize(sheet);

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to generate sms history excel", e);
        }
    }

    private void writeHeader(XSSFSheet sheet) {
        Row header = sheet.createRow(0);

        String[] headers = {
                "ID",
                "Reference Type",
                "Reference ID",
                "User Name",
                "Class Number",
                "Phone Number",
                "Message Type",
                "Club Name",
                "Status",
                "Scheduled At",
                "Sent At",
                "Failure Reason",
                "Created At",
                "Updated At"
        };

        for (int i = 0; i < headers.length; i++) {
            header.createCell(i).setCellValue(headers[i]);
        }
    }

    private void writeRows(XSSFSheet sheet, List<SmsHistory> smsHistories) {
        int rowIndex = 1;

        for (SmsHistory smsHistory : smsHistories) {
            Row row = sheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(smsHistory.getId());
            row.createCell(1).setCellValue(smsHistory.getReferenceType().name());
            row.createCell(2).setCellValue(smsHistory.getReferenceId());
            row.createCell(3).setCellValue(smsHistory.getUserName());
            row.createCell(4).setCellValue(smsHistory.getClassNumber());
            row.createCell(5).setCellValue(smsHistory.getPhoneNumber());
            row.createCell(6).setCellValue(smsHistory.getMessageType());
            row.createCell(7).setCellValue(smsHistory.getClubName());
            row.createCell(8).setCellValue(smsHistory.getStatus().name());
            row.createCell(9).setCellValue(format(smsHistory.getScheduledAt()));
            row.createCell(10).setCellValue(format(smsHistory.getSentAt()));
            row.createCell(11).setCellValue(orEmpty(smsHistory.getFailureReason()));
            row.createCell(12).setCellValue(format(smsHistory.getCreatedAt()));
            row.createCell(13).setCellValue(format(smsHistory.getUpdatedAt()));
        }
    }

    private void autoSize(XSSFSheet sheet) {
        Row header = sheet.getRow(0);
        if (header == null) {
            return;
        }

        for (Cell cell : header) {
            sheet.autoSizeColumn(cell.getColumnIndex());
        }
    }

    private String format(LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.format(DATE_TIME_FORMATTER);
    }

    private String orEmpty(String value) {
        return value == null ? "" : value;
    }
}
