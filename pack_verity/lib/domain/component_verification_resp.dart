class ComponentVerificationResp {
  final String? status;
  final String? statusDescription;
  final ComponentVerificationDto? data;

  ComponentVerificationResp({this.status, this.statusDescription, this.data});

  factory ComponentVerificationResp.fromJson(Map<String, dynamic> json) {
    return ComponentVerificationResp(
      status: json['status'],
      statusDescription: json['statusDescription'],
      data: json['data'] != null
          ? ComponentVerificationDto.fromJson(json['data'])
          : null,
    );
  }

  bool isSuccess() {
    return status == "S1000";
  }
}

class ComponentVerificationDto {
  final String? componentCode;
  final String? componentType;
  final String? componentStatus;
  final String? hhDeviceCode;

  ComponentVerificationDto(
      {this.componentCode,
      this.componentType,
      this.componentStatus,
      this.hhDeviceCode});

  factory ComponentVerificationDto.fromJson(Map<String, dynamic> json) {
    return ComponentVerificationDto(
      componentCode: json['componentCode'],
      componentType: json['componentType'],
      componentStatus: json['componentStatus'],
      hhDeviceCode: json['hhDeviceCode'],
    );
  }
}
