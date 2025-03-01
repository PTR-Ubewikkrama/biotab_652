import * as XLSX from "xlsx";
import { List } from "reselect/es/types";
import { OverPressureTest } from "../../../services/over_pressure_test_service";

export function handleGenerateOverPressureTesExcel(datas: List<OverPressureTest>) {
  // Create a new workbook
  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Test ID",
      "Device Mac",
      "Qr Code",
      "Max Pressure",
      "Max Pressure Time",
      "Max Pressure Flow Rate",
      "Normal Pressure",
      "Normal Pressure Time",
      "Normal Pressure Flow Rate",
      "Over Pressure Valve Status",
      "Status",
      "Date Time",
    ],
    ...datas.map((data) => [
      data.testId,
      data.deviceMac,
      data.qrCode,
      data.maxPressure,
      data.maxPressureTime,
      data.maxPressureFlowRate,
      data.normalPressure,
      data.normalPressureTime,
      data.normalPressureFlowRate,
      data.overPressureValveStatus ? "Pass" : "Fail",
      data.status ? "Pass" : "Fail",
      data.dateTime.split("T")[0],
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Over_Pressure_Test_" + new Date().toISOString() + ".xlsx");
}
