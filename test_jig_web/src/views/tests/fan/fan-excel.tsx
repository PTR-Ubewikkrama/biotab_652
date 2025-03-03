import * as XLSX from "xlsx";
import { format, parseISO } from "date-fns";
import { FanTest } from "../../../services/fan_test_service";

export function handleGenerateValveCardExcel(datas: FanTest[]) {

  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Serial Number",
      "Visual Inspection",
      "Draw Current",
      "Draw Current State",
      "Fan Speed",
      "Fan Speed State",
      "Overall Fan State",
      "Status",
      "Date Time"

    ],
    ...datas.map((data) => [
      data.serialNumber,
      data.visualInspection ? "Pass" : "Fail",
      data.drawCurrent,
      data.drawCurrentState ? "Pass" : "Fail",
      data.fanSpeed,
      data.fanSpeedState ? "Pass" : "Fail",
      data.overallFanState ? "Pass" : "Fail",
      data.status && data.status ? "Pass" : "Fail",
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Fan_Test_" + new Date().toISOString() + ".xlsx");
}
