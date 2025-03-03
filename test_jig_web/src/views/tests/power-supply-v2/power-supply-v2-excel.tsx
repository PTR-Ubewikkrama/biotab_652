import * as XLSX from "xlsx";
import { List } from "reselect/es/types";
import { PowerSupplyV2Test } from "../../../services/powerSupply_service";
import { format, parseISO } from "date-fns";

export function handleGeneratePowerSupplyV2Excel(datas: List<PowerSupplyV2Test>) {

  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Test Id",
      "Serial Number",
      "idleVolLowTh",
      "idleVolUpTh",
      "loadVolLowTh",
      "loadVolUpTh",
      "loadCurUpTh",
      "Ideal Voltage",
      "Load Voltage",
      "Load Voltage Status",
      "Load Current",
      "Load Current Status",
      "Operating Power",
      "Date",
      "Status",
    ],
    ...datas.map((data) => [
      data.testId,
      data.serialNumber,
      data.idleVoltageLowTh,
      data.idleVoltageUpTh,
      data.loadVoltageLowTh,
      data.loadVoltageUpTh,
      data.loadCurrentUpTh,
      data.idleVol,
      data.loadVol,
      data.loadVolStatus && data.loadVolStatus ? "Pass" : "Fail",
      data.loadCurrent,
      data.loadCurrentStatus ? "Pass" : "Fail",
      data.operatingPower,
      format(parseISO(data.dateTime), "yyyy-MM-dd HH:mm:ss"),
      data.status && data.status ? "Pass" : "Fail",
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "Power_Supply_v2_Test_" + new Date().toISOString() + ".xlsx");
}
