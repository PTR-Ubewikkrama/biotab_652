class GetBTDeviceResponse {
  final String status;
  final String statusDescription;
  final BTDevice? data;

  GetBTDeviceResponse(
      {required this.status, required this.statusDescription, this.data});

  factory GetBTDeviceResponse.fromJson(Map<String, dynamic> json) {
    return GetBTDeviceResponse(
      status: json['status'] ?? '',
      statusDescription: json['statusDescription'] ?? '',
      data: json['data'] != null ? BTDevice.fromJson(json['data']) : null,
    );
  }

  bool isSuccess() {
    return status == "S1000";
  }
}

class BTDevice {
  final int? deviceId;
  final String? deviceCode;
  final String? powerPcbCode;
  final String? powerPcbCodeStatus;
  final String? pumpCode;
  final String? pumpCodeStatus;
  final String? fanCode;
  final String? fanCodeStatus;
  final String? uiPcbCode;
  final String? uiPcbCodeStatus;
  final String? encoderCode;
  final String? encoderCodeStatus;
  final String? mainPcbCode;
  final String? mainPcbCodeStatus;
  final String? manifoldCode;
  final String? manifoldCodeStatus;
  final String? valveCardInsideCableSetCode;
  final String? valveCardInsideCableSetCodeStatus;
  final String? valveCardInputOutputCableSetCode;
  final String? valveCardInputOutputCableSetCodeStatus;
  final String? overPressureValveCode;
  final String? overPressureValveCodeStatus;
  final String? powerCableCode;
  final String? uiCableCode;
  final String? displayCode;
  final String? frontBracketAssemblyCode;
  final String? frontBracketAssemblyCodeStatus;
  final String? powerAdaptorCode;
  final String? powerAdaptorCodeStatus;
  final String? enclosureTopCode;
  final String? enclosureBottomCode;
  final String? backVentCode;
  final String? fanMountCode;
  final String? encoderSupporterCode;
  final String? pcbHolderCode;
  final List<String>? valveCards;
  final String? createdBy;
  final String? dateTime;

  BTDevice(
      {this.deviceId,
      this.deviceCode,
      this.powerPcbCode,
      this.powerPcbCodeStatus,
      this.pumpCode,
      this.pumpCodeStatus,
      this.fanCode,
      this.fanCodeStatus,
      this.uiPcbCode,
      this.uiPcbCodeStatus,
      this.encoderCode,
      this.encoderCodeStatus,
      this.mainPcbCode,
      this.mainPcbCodeStatus,
      this.manifoldCode,
      this.manifoldCodeStatus,
      this.valveCardInsideCableSetCode,
      this.valveCardInsideCableSetCodeStatus,
      this.valveCardInputOutputCableSetCode,
      this.valveCardInputOutputCableSetCodeStatus,
      this.overPressureValveCode,
      this.overPressureValveCodeStatus,
      this.powerCableCode,
      this.uiCableCode,
      this.displayCode,
      this.frontBracketAssemblyCode,
      this.frontBracketAssemblyCodeStatus,
      this.powerAdaptorCode,
      this.powerAdaptorCodeStatus,
      this.enclosureTopCode,
      this.enclosureBottomCode,
      this.backVentCode,
      this.fanMountCode,
      this.encoderSupporterCode,
      this.pcbHolderCode,
      this.valveCards,
      this.createdBy,
      this.dateTime});

  factory BTDevice.fromJson(Map<String, dynamic> json) {
    return BTDevice(
      deviceId: json['deviceId'],
      deviceCode: json['deviceCode'],
      powerPcbCode: json['powerPcbCode'],
      powerPcbCodeStatus: json['powerPcbCodeStatus'],
      pumpCode: json['pumpCode'],
      pumpCodeStatus: json['pumpCodeStatus'],
      fanCode: json['fanCode'],
      fanCodeStatus: json['fanCodeStatus'],
      uiPcbCode: json['uiPcbCode'],
      uiPcbCodeStatus: json['uiPcbCodeStatus'],
      encoderCode: json['encoderCode'],
      encoderCodeStatus: json['encoderCodeStatus'],
      mainPcbCode: json['mainPcbCode'],
      mainPcbCodeStatus: json['mainPcbCodeStatus'],
      manifoldCode: json['manifoldCode'],
      manifoldCodeStatus: json['manifoldCodeStatus'],
      valveCardInsideCableSetCode: json['valveCardInsideCableSetCode'],
      valveCardInsideCableSetCodeStatus:
          json['valveCardInsideCableSetCodeStatus'],
      valveCardInputOutputCableSetCode:
          json['valveCardInputOutputCableSetCode'],
      valveCardInputOutputCableSetCodeStatus:
          json['valveCardInputOutputCableSetCodeStatus'],
      overPressureValveCode: json['overPressureValveCode'],
      overPressureValveCodeStatus: json['overPressureValveCodeStatus'],
      powerCableCode: json['powerCableCode'],
      uiCableCode: json['uiCableCode'],
      displayCode: json['displayCode'],
      frontBracketAssemblyCode: json['frontBracketAssemblyCode'],
      frontBracketAssemblyCodeStatus: json['frontBracketAssemblyCodeStatus'],
      powerAdaptorCode: json['powerAdaptorCode'],
      powerAdaptorCodeStatus: json['powerAdaptorCodeStatus'],
      enclosureTopCode: json['enclosureTopCode'],
      enclosureBottomCode: json['enclosureBottomCode'],
      backVentCode: json['backVentCode'],
      fanMountCode: json['fanMountCode'],
      encoderSupporterCode: json['encoderSupporterCode'],
      pcbHolderCode: json['pcbHolderCode'],
      valveCards: json['valveCards'] != null
          ? List<String>.from(json['valveCards'])
          : null,
      createdBy: json['createdBy'],
      dateTime: json['dateTime'],
    );
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['deviceId'] = deviceId;
    data['deviceCode'] = deviceCode;
    data['powerPcbCode'] = powerPcbCode;
    data['powerPcbCodeStatus'] = powerPcbCodeStatus;
    data['pumpCode'] = pumpCode;
    data['pumpCodeStatus'] = pumpCodeStatus;
    data['fanCode'] = fanCode;
    data['fanCodeStatus'] = fanCodeStatus;
    data['uiPcbCode'] = uiPcbCode;
    data['uiPcbCodeStatus'] = uiPcbCodeStatus;
    data['encoderCode'] = encoderCode;
    data['encoderCodeStatus'] = encoderCodeStatus;
    data['mainPcbCode'] = mainPcbCode;
    data['mainPcbCodeStatus'] = mainPcbCodeStatus;
    data['manifoldCode'] = manifoldCode;
    data['manifoldCodeStatus'] = manifoldCodeStatus;
    data['valveCardInsideCableSetCode'] = valveCardInsideCableSetCode;
    data['valveCardInsideCableSetCodeStatus'] =
        valveCardInsideCableSetCodeStatus;
    data['valveCardInputOutputCableSetCode'] = valveCardInputOutputCableSetCode;
    data['valveCardInputOutputCableSetCodeStatus'] =
        valveCardInputOutputCableSetCodeStatus;
    data['overPressureValveCode'] = overPressureValveCode;
    data['overPressureValveCodeStatus'] = overPressureValveCodeStatus;
    data['powerCableCode'] = powerCableCode;
    data['uiCableCode'] = uiCableCode;
    data['displayCode'] = displayCode;
    data['frontBracketAssemblyCode'] = frontBracketAssemblyCode;
    data['frontBracketAssemblyCodeStatus'] = frontBracketAssemblyCodeStatus;
    data['powerAdaptorCode'] = powerAdaptorCode;
    data['powerAdaptorCodeStatus'] = powerAdaptorCodeStatus;
    data['enclosureTopCode'] = enclosureTopCode;
    data['enclosureBottomCode'] = enclosureBottomCode;
    data['backVentCode'] = backVentCode;
    data['fanMountCode'] = fanMountCode;
    data['encoderSupporterCode'] = encoderSupporterCode;
    data['pcbHolderCode'] = pcbHolderCode;
    data['valveCards'] = valveCards;
    data['createdBy'] = createdBy;
    data['dateTime'] = dateTime;
    return data;
  }
}
