import * as XLSX from "xlsx";
import { List } from "reselect/es/types";
import { LatchButtonTest } from "../../../services/latch_button_test_service";

export function handleGenerateLatchButtonExcel(datas: List<LatchButtonTest>) {
  // Create a new workbook
  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Test ID",
      "Device Mac",
      "Qr Code",
      "Button On Test Status",
      "Button Off Test Status",
      "Led On Test Status",
      "Latch Button Status",
      "Date Time",
    ],
    ...datas.map((data) => [
      data.testId,
      data.deviceMac,
      data.qrCode,
      data.buttonOnTestStatus ? "Pass" : "Fail",
      data.buttonOffTestStatus ? "Pass" : "Fail",
      data.ledOnTestStatus ? "Pass" : "Fail",
      data.latchButtonStatus ? "Pass" : "Fail",
      data.dateTime.split("T")[0] + " " + data.dateTime.split("T")[1].split(".")[0],
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Latch_Button_Test_" + new Date().toISOString() + ".xlsx");
}
