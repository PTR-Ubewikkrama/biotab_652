class ValidateComponentResp {
  final String status;
  final String statusDescription;
  final ValidateComponentDto? data;

  ValidateComponentResp(
      {required this.status,
      required this.statusDescription,
      required this.data});

  factory ValidateComponentResp.fromJson(Map<String, dynamic> json) {
    return ValidateComponentResp(
      status: json['status'] ?? '',
      statusDescription: json['statusDescription'] ?? '',
      data: ValidateComponentDto.fromJson(json['data']),
    );
  }

  bool isSuccess() {
    return status == "S1000";
  }
}

class ValidateComponentDto {
  final String? hhDeviceCode;

  ValidateComponentDto({required this.hhDeviceCode});

  factory ValidateComponentDto.fromJson(Map<String, dynamic> json) {
    return ValidateComponentDto(
      hhDeviceCode: json['hhDeviceCode'] ?? '',
    );
  }
}
