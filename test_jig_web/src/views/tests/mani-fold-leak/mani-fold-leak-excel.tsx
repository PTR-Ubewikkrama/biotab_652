import * as XLSX from "xlsx";
import { format, parseISO } from "date-fns";
import { ManiFoldLeakTest } from "../../../services/mani_fold_leak_service";

export function handleGenerateManiFoldLeakExcel(datas: ManiFoldLeakTest[]) {

  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Serial Number",
      "Device Mac",
      "Location",
      "Physical Inspection State",
      "Leakage Flowrate",
      "Manifold Leak State",
      "Overall Manifold Leak State",
      "Status",
      "Date Time"

    ],
    ...datas.map((data) => [
      data.serialNumber,
      data.deviceMac,
      data.location,
      data.physicalInspectionState ? "Pass" : "Fail",
      data.leakageFlowrate,
      data.manifoldLeakState ? "Pass" : "Fail",
      data.overallManifoldLeakState ? "Pass" : "Fail",
      data.status && data.status ? "Pass" : "Fail",
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "ManiFold_Leak_Test_" + new Date().toISOString() + ".xlsx");
}
