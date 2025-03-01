import 'package:pack_verity/domain/fa_resp.dart';
import 'package:pack_verity/domain/hh_device_resp.dart';

class GetCartoonPackageResponse {
  final String status;
  final String statusDescription;
  final GetFinalAssemblyByIdResponse? data;

  GetCartoonPackageResponse(
      {required this.status, required this.statusDescription, this.data});

  factory GetCartoonPackageResponse.fromJson(Map<String, dynamic> json) {
    return GetCartoonPackageResponse(
      status: json['status'] ?? '',
      statusDescription: json['statusDescription'] ?? '',
      data: json['data'] != null
          ? GetFinalAssemblyByIdResponse.fromJson(json['data'])
          : null,
    );
  }

  bool isSuccess() {
    return status == "S1000";
  }
}

class GetFinalAssemblyByIdResponse {
  final FADto? finalAssembly;
  final CartoonBoxDto? cartoonBox;
  final HhDevice device;

  GetFinalAssemblyByIdResponse(
      {required this.finalAssembly,
      required this.cartoonBox,
      required this.device});

  factory GetFinalAssemblyByIdResponse.fromJson(Map<String, dynamic> json) {
    return GetFinalAssemblyByIdResponse(
      finalAssembly: json['finalAssembly'] != null
          ? FADto.fromJson(json['finalAssembly'])
          : null,
      cartoonBox: json['cartoonBox'] != null
          ? CartoonBoxDto.fromJson(json['cartoonBox'])
          : null,
      device: HhDevice.fromJson(json['device']),
    );
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (finalAssembly != null) {
      data['finalAssembly'] = finalAssembly!.toJson();
    }
    if (cartoonBox != null) {
      data['cartoonBox'] = cartoonBox!.toJson();
    }
    data['device'] = device.toJson();
    return data;
  }
}

class CartoonBoxDto {
  final int? id;
  final String? cartoonNumber;
  final String? createdAt;
  final String? updatedAt;
  final String? createdBy;

  CartoonBoxDto(
      {this.id,
      this.cartoonNumber,
      this.createdAt,
      this.updatedAt,
      this.createdBy});

  factory CartoonBoxDto.fromJson(Map<String, dynamic> json) {
    return CartoonBoxDto(
      id: json['id'],
      cartoonNumber: json['cartoonNumber'],
      createdAt: json['createdAt'],
      updatedAt: json['updatedAt'],
      createdBy: json['createdBy'],
    );
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['cartoonNumber'] = cartoonNumber;
    data['createdAt'] = createdAt;
    data['updatedAt'] = updatedAt;
    data['createdBy'] = createdBy;
    return data;
  }
}
