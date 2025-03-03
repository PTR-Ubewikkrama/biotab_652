import * as XLSX from "xlsx";
import { format, parseISO } from "date-fns";
import { CableTest } from "../../../services/cable_test_service";

export function handleGenerateValveCardExcel(datas: CableTest[]) {

  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Serial Number",
      "Cable Selection",
      "Visual Inspection",
      "Cable 1",
      "Cable 2",
      "Cable 3",
      "Cable 4",
      "Cable 5",
      "Cable 6",
      "Cable 7",
      "Cable 8",
      "Cable 9",
      "Cable 10",
      "Overall Cable State",
      "Status",
      "Date Time"

    ],
    ...datas.map((data) => [
      data.serialNumber,
      data.cableSelection,
      data.visualInspection,
      data.cable1,
      data.cable2,
      data.cable3,
      data.cable4,
      data.cable5,
      data.cable6,
      data.cable7,
      data.cable8,
      data.cable9,
      data.cable10,
      data.overallCableState,
      data.status && data.status ? "Pass" : "Fail",
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Cable_Test_" + new Date().toISOString() + ".xlsx");
}
