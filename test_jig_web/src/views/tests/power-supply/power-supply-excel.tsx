import * as XLSX from "xlsx";
import { List } from "reselect/es/types";
import { PowerSupplyTest } from "../../../services/powerSupply_service";

export function handleGeneratePowerSupplyExcel(datas: List<PowerSupplyTest>) {

  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Test Id",
      "Device Id",
      "idleVolLowTh",
      "idleVolUpTh",
      "loadVolLowTh",
      "loadVolUpTh",
      "loadCurUpTh",
      "Serial Number",
      "Ideal Voltage",
      "Load Voltage",
      "Load Current",
      "Operating Power",
      "Date",
      "Device Status",
    ],
    ...datas.map((data) => [
      data.testId,
      data.deviceId,
      data.idleVolLowTh,
      data.idleVolUpTh,
      data.loadVolLowTh,
      data.loadVolUpTh,
      data.loadCurUpTh,
      data.serialNumber,
      data.idleVol,
      data.loadVol,
      data.loadCurrent,
      data.operatingPower,
      data.dateTime.split("T")[0],
      (data.idleVolStatus && data.loadVolStatus && data.loadCurrentStatus) ? "Pass" : "Fail",
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Power_Supply_Test_" + new Date().toISOString() + ".xlsx");
}
