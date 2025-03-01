import 'package:pack_verity/domain/fa_resp.dart';

class ValidateDeviceForStageTwoResponse {
  final String status;
  final String statusDescription;
  final ValidateDeviceForStageTwoResponseData? data;

  ValidateDeviceForStageTwoResponse(
      {required this.status,
      required this.statusDescription,
      required this.data});

  factory ValidateDeviceForStageTwoResponse.fromJson(
      Map<String, dynamic> json) {
    return ValidateDeviceForStageTwoResponse(
      status: json['status'] ?? '',
      statusDescription: json['statusDescription'] ?? '',
      data: json['data'] != null
          ? ValidateDeviceForStageTwoResponseData.fromJson(json['data'])
          : null,
    );
  }

  bool isSuccess() {
    return status == "S1000";
  }
}

class ValidateDeviceForStageTwoResponseData {
  final FADto? finalAssembly;

  ValidateDeviceForStageTwoResponseData({required this.finalAssembly});

  factory ValidateDeviceForStageTwoResponseData.fromJson(
      Map<String, dynamic> json) {
    return ValidateDeviceForStageTwoResponseData(
      finalAssembly: json['finalAssembly'] != null
          ? FADto.fromJson(json['finalAssembly'])
          : null,
    );
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (finalAssembly != null) {
      data['finalAssembly'] = finalAssembly!.toJson();
    }
    return data;
  }
}
