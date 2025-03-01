class GetHHDeviceResponse {
  final String status;
  final String statusDescription;
  final HhDevice? data;

  GetHHDeviceResponse(
      {required this.status, required this.statusDescription, this.data});

  factory GetHHDeviceResponse.fromJson(Map<String, dynamic> json) {
    return GetHHDeviceResponse(
      status: json['status'] ?? '',
      statusDescription: json['statusDescription'] ?? '',
      data: json['data'] != null ? HhDevice.fromJson(json['data']) : null,
    );
  }

  bool isSuccess() {
    return status == "S1000";
  }
}

class HhDevice {
  final int? deviceId;
  final String? deviceCode;
  final String? deviceCodeStatus;
  final String? pcbTestCode;
  final String? pcbTestCodeStatus;
  final String? valveTestOneCode;
  final String? valveTestOneCodeStatus;
  final String? valveTestTwoCode;
  final String? valveTestTwoCodeStatus;
  final String? airPumpTestCode;
  final String? airPumpTestCodeStatus;
  final String? latchButtonTestCode;
  final String? latchButtonTestCodeStatus;
  final String? overPressureValveTestCode;
  final String? overPressureValveTestCodeStatus;
  final String? batteryTestCode;
  final String? batteryTestCodeStatus;
  final String? enclosureCode;
  final String? enclosureCodeStatus;
  final String? airBladderCode;
  final String? airBladderCodeStatus;
  final String? powerSupplyTestCode;
  final String? powerSupplyTestCodeStatus;
  final String? createdBy;
  final String? dateTime;

  HhDevice(
      {this.deviceId,
      this.deviceCode,
      this.deviceCodeStatus,
      this.pcbTestCode,
      this.pcbTestCodeStatus,
      this.valveTestOneCode,
      this.valveTestOneCodeStatus,
      this.valveTestTwoCode,
      this.valveTestTwoCodeStatus,
      this.airPumpTestCode,
      this.airPumpTestCodeStatus,
      this.latchButtonTestCode,
      this.latchButtonTestCodeStatus,
      this.overPressureValveTestCode,
      this.overPressureValveTestCodeStatus,
      this.batteryTestCode,
      this.batteryTestCodeStatus,
      this.enclosureCode,
      this.enclosureCodeStatus,
      this.airBladderCode,
      this.airBladderCodeStatus,
      this.powerSupplyTestCode,
      this.powerSupplyTestCodeStatus,
      this.createdBy,
      this.dateTime});

  factory HhDevice.fromJson(Map<String, dynamic> json) {
    return HhDevice(
      deviceId: json['deviceId'],
      deviceCode: json['deviceCode'],
      deviceCodeStatus: json['deviceCodeStatus'],
      pcbTestCode: json['pcbTestCode'],
      pcbTestCodeStatus: json['pcbTestCodeStatus'],
      valveTestOneCode: json['valveTestOneCode'],
      valveTestTwoCodeStatus: json['valveTestTwoCodeStatus'],
      valveTestTwoCode: json['valveTestTwoCode'],
      valveTestOneCodeStatus: json['valveTestOneCodeStatus'],
      airPumpTestCodeStatus: json['airPumpTestCodeStatus'],
      airPumpTestCode: json['airPumpTestCode'],
      latchButtonTestCodeStatus: json['latchButtonTestCodeStatus'],
      latchButtonTestCode: json['latchButtonTestCode'],
      overPressureValveTestCodeStatus: json['overPressureValveTestCodeStatus'],
      overPressureValveTestCode: json['overPressureValveTestCode'],
      batteryTestCodeStatus: json['batteryTestCodeStatus'],
      batteryTestCode: json['batteryTestCode'],
      enclosureCodeStatus: json['enclosureCodeStatus'],
      enclosureCode: json['enclosureCode'],
      airBladderCodeStatus: json['airBladderCodeStatus'],
      airBladderCode: json['airBladderCode'],
      powerSupplyTestCodeStatus: json['powerSupplyTestCodeStatus'],
      powerSupplyTestCode: json['powerSupplyTestCode'],
      createdBy: json['createdBy'],
      dateTime: json['dateTime'],
    );
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['deviceId'] = deviceId;
    data['deviceCode'] = deviceCode;
    data['deviceCodeStatus'] = deviceCodeStatus;
    data['pcbTestCode'] = pcbTestCode;
    data['pcbTestCodeStatus'] = pcbTestCodeStatus;
    data['valveTestOneCode'] = valveTestOneCode;
    data['valveTestOneCodeStatus'] = valveTestOneCodeStatus;
    data['valveTestTwoCode'] = valveTestTwoCode;
    data['valveTestTwoCodeStatus'] = valveTestTwoCodeStatus;
    data['airPumpTestCode'] = airPumpTestCode;
    data['airPumpTestCodeStatus'] = airPumpTestCodeStatus;
    data['latchButtonTestCode'] = latchButtonTestCode;
    data['latchButtonTestCodeStatus'] = latchButtonTestCodeStatus;
    data['overPressureValveTestCode'] = overPressureValveTestCode;
    data['overPressureValveTestCodeStatus'] = overPressureValveTestCodeStatus;
    data['batteryTestCode'] = batteryTestCode;
    data['batteryTestCodeStatus'] = batteryTestCodeStatus;
    data['enclosureCode'] = enclosureCode;
    data['enclosureCodeStatus'] = enclosureCodeStatus;
    data['airBladderCode'] = airBladderCode;
    data['airBladderCodeStatus'] = airBladderCodeStatus;
    data['powerSupplyTestCode'] = powerSupplyTestCode;
    data['powerSupplyTestCodeStatus'] = powerSupplyTestCodeStatus;
    data['createdBy'] = createdBy;
    data['dateTime'] = dateTime;
    return data;
  }
}
