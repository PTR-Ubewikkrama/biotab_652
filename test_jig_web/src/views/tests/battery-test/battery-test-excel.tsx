import * as XLSX from "xlsx";
import { List } from "reselect/es/types";
import { BatteryTest } from "../../../services/battery_test_service";

export function handleGenerateBatteryTestExcel(datas: List<BatteryTest>) {
  // Create a new workbook
  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Test ID",
      "Device Mac",
      "Qr Code",
      "Maximum Current",
      "Max Current Drawn Time",
      "Max Current Cut Off",
      "Normal Current",
      "Normal Current Drawn Time",
      "Normal Current Cut Off",
      "Battery Status",
      "Date Time",
    ],
    ...datas.map((data) => [
      data.testId,
      data.deviceMac,
      data.qrCode,
      data.maximumCurrent,
      data.maxCurrentDrawnTime,
      data.maxCurrentCutOff ? "Pass" : "Fail",
      data.normalCurrent,
      data.normalCurrentDrawnTime,
      data.normalCurrentCutOff ? "Pass" : "Fail",
      data.batteryStatus ? "Pass" : "Fail",
      data.dateTime.split("T")[0],
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Battery_Test_" + new Date().toISOString() + ".xlsx");
}
