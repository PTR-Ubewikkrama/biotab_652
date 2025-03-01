import * as XLSX from "xlsx";
import { List } from "reselect/es/types";
import { FinalAssemblyDto } from "../../services/fa_service";

export function handleGenerateFAExcel(datas: List<FinalAssemblyDto>) {
  // Create a new workbook
  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Device Code",
      "Category",
      "Bladder Code",
      "UPL Number",
      "UDI Number",
      "Adapter Code",
      "Cartoon Number",
      "Created At",
      "Updated At",
      "Updated By",
      "Created By"
    ],
    ...datas.map((data) => [
      data.deviceCode,
      data.category,
      data.bladderCode,
      data.uplNumber,
      data.udiNumber,
      data.adapterCode,
      data.cartoonNumber,
      data.createdAt,
      data.updatedAt,
      data.updatedBy != null ? data.updatedAt.split("T")[0] : "",
      data.createdBy != null ? data.createdAt.split("T")[0] : "",
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Final_Assembly_" + new Date().toISOString() + ".xlsx");
}
