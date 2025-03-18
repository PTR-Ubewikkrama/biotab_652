import * as XLSX from "xlsx";
import { format, parseISO } from "date-fns";
import { MainPCBTest } from "../../../services/Main_PCB_test_service";

export function handleGenerateMainPCBExcel(datas: MainPCBTest[]) {

  const workbook = XLSX.utils.book_new();

  const worksheetData = [
    [
      "Serial Number",
      "Software Version",
      "Batch Number",
      "Overall Status",
      "Date Time",
      "Test Name",
      "Actual value",
      "Test type",
      "Min Value",
      "Max value",
      "Status",
      "Date Time of test"
    ],
  ];

  datas.forEach((data) => {
    worksheetData.push([
      data.serialNumber,
      data.softwareVersion,
      data.batchNumber,
      data.status && data.status ? "Pass" : "Fail",
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]);

    data.testResultData.forEach((testResult) => {
      worksheetData.push([
        "", // Skip Serial Number
        "", // Skip Software Version
        "", // Skip Batch Number
        "", // Skip Status
        "", // Skip Date Time
        testResult.testName,
        testResult.actualValue,
        testResult.testType,
        testResult.minValue.toString(),
        testResult.maxValue.toString(),
        testResult.status ? "PASS" : "FAIL",
        format(parseISO(testResult.dateTime), "yyyy-MM-dd HH:mm:ss"),
      ]);
    });
  });

  const worksheet = XLSX.utils.aoa_to_sheet(worksheetData);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Main_PCB_Test_" + new Date().toISOString() + ".xlsx");
}
