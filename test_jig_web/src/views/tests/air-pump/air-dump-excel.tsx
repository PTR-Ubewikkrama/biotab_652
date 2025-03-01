import * as XLSX from "xlsx";
import { List } from "reselect/es/types";
import { AirPumpTest } from "../../../services/airPump_service";

export function handleGenerateAirDumpExcel(datas: List<AirPumpTest>) {
  // Create a new workbook
  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Serial Number",
      "Voltage",
      "Current",
      "Max Pressure",
      "Noise Level",
      "Date",
      "Device Status",
      "Running time",
      "Voltage Lower value",
      "Voltage Upper value",
      "Max Current value",
      "Load_Vol_Low_th",
      "Set_Pressure",
    ],
    ...datas.map((data) => [
      data.serialNumber,
      data.idleVol,
      data.idleCurrent,
      data.maxPressure,
      (data.noiseLevel) ? "Yes" : "No",
      data.dateTime?.split("T")[0],
      (data.idleVolStatus && data.idleCurrentStatus) ? "Pass" : "Fail",
      data.idleCurUpTh,
      data.idleVolLowTh,
      data.idleVolUpTh,
      data.loadCurUpTh,
      data.loadVolLowTh,
      data.setPressure,


    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Air_Pump_Test_" + new Date().toISOString() + ".xlsx");
}
