import * as XLSX from "xlsx";
import { List } from "reselect/es/types";
import { DeviceHHDto } from "../../services/hh_device_service";

export function handleGenerateHHDeviceExcel(datas: List<DeviceHHDto>) {
  // Create a new workbook
  const workbook = XLSX.utils.book_new();

  const worksheet = XLSX.utils.aoa_to_sheet([
    [
      "Device Code",
      "Device Code Status",
      "PCB Code",
      "PCB Code Status",
      "Valve Test One Code",
      "Valve Test One Code Status",
      "Valve Test Two Code",
      "Valve Test Two Code Status",
      "Air Pump Test Code",
      "Air Pump Test Code Status",
      "Latch Button Test Code",
      "Latch Button Test Code Status",
      "Over Pressure Valve Test Code",
      "Over Pressure Valve Test Code Status",
      "Battery Test Code",
      "Battery Test Code Status",
      "Enclosure Code",
      "Enclosure Code Status",
      "Air Bladder Code",
      "Air Bladder Code Status",
      "Power Supply Test Code",
      "Power Supply Test Code Status",
      "Created By",
      "Date Time",
    ],
    ...datas.map((data) => [
      data.deviceCode,
      data.deviceCodeStatus,
      data.pcbTestCode,
      data.pcbTestCodeStatus,
      data.valveTestOneCode,
      data.valveTestOneCodeStatus,
      data.valveTestTwoCode,
      data.valveTestTwoCodeStatus,
      data.airPumpTestCode,
      data.airPumpTestCodeStatus,
      data.latchButtonTestCode,
      data.latchButtonTestCodeStatus,
      data.overPressureValveTestCode,
      data.overPressureValveTestCodeStatus,
      data.batteryTestCode,
      data.batteryTestCodeStatus,
      data.enclosureCode,
      data.enclosureCodeStatus,
      data.airBladderCode,
      data.airBladderCodeStatus,
      data.powerSupplyTestCode,
      data.powerSupplyTestCodeStatus,
      data.createdBy,
      data.dateTime.split("T")[0],
    ]),
  ]);

  // Add the worksheet to the workbook
  XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

  // Save the workbook as an Excel file
  XLSX.writeFile(workbook, "HH_Device_" + new Date().toISOString() + ".xlsx");
}
