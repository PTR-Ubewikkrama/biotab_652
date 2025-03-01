import 'package:pack_verity/domain/fa_resp.dart';

class GetCartoonPackageUIDsByIdResponse {
  final String status;
  final String statusDescription;
  final GetCartoonPackageUIDsByIdResponseData? data;

  GetCartoonPackageUIDsByIdResponse(
      {required this.status,
      required this.statusDescription,
      required this.data});

  factory GetCartoonPackageUIDsByIdResponse.fromJson(
      Map<String, dynamic> json) {
    return GetCartoonPackageUIDsByIdResponse(
      status: json['status'] ?? '',
      statusDescription: json['statusDescription'] ?? '',
      data: json['data'] != null
          ? GetCartoonPackageUIDsByIdResponseData.fromJson(json['data'])
          : null,
    );
  }

  bool isSuccess() {
    return status == "S1000";
  }
}

class GetCartoonPackageUIDsByIdResponseData {
  final List<String>? uids;
  final String? cartoonNumber;

  GetCartoonPackageUIDsByIdResponseData(
      {required this.uids, required this.cartoonNumber});

  factory GetCartoonPackageUIDsByIdResponseData.fromJson(
      Map<String, dynamic> json) {
    return GetCartoonPackageUIDsByIdResponseData(
      uids: json['uids'] != null ? List<String>.from(json['uids']) : null,
      cartoonNumber: json['cartoonNumber'] ?? '',
    );
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (uids != null) {
      data['uids'] = uids;
    }
    if (cartoonNumber != null) {
      data['cartoonNumber'] = cartoonNumber;
    }
    return data;
  }
}
