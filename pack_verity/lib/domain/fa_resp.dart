class GetFAResponse {
  final String status;
  final String statusDescription;
  final FADto? data;

  GetFAResponse(
      {required this.status, required this.statusDescription, this.data});

  factory GetFAResponse.fromJson(Map<String, dynamic> json) {
    return GetFAResponse(
      status: json['status'] ?? '',
      statusDescription: json['statusDescription'] ?? '',
      data: json['data'] != null ? FADto.fromJson(json['data']) : null,
    );
  }

  bool isSuccess() {
    return status == "S1000";
  }
}

class FADto {
  final String? deviceCode;
  final String? category;
  final String? bladderCode;
  final String? uplNumber;
  final String? udiNumber;
  final String? adapterCode;
  final String? cartoonNumber;
  final String? createdAt;
  final String? updatedAt;
  final String? updatedBy;
  final String? createdBy;

  FADto(
      {this.deviceCode,
      this.category,
      this.bladderCode,
      this.uplNumber,
      this.udiNumber,
      this.adapterCode,
      this.cartoonNumber,
      this.createdAt,
      this.updatedAt,
      this.updatedBy,
      this.createdBy});

  factory FADto.fromJson(Map<String, dynamic> json) {
    return FADto(
      deviceCode: json['deviceCode'],
      category: json['category'],
      bladderCode: json['bladderCode'],
      uplNumber: json['uplNumber'],
      udiNumber: json['udiNumber'],
      adapterCode: json['adapterCode'],
      cartoonNumber: json['cartoonNumber'],
      createdAt: json['createdAt'],
      updatedAt: json['updatedAt'],
      updatedBy: json['updatedBy'],
      createdBy: json['createdBy'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'deviceCode': deviceCode,
      'category': category,
      'bladderCode': bladderCode,
      'uplNumber': uplNumber,
      'udiNumber': udiNumber,
      'adapterCode': adapterCode,
      'cartoonNumber': cartoonNumber,
      'createdAt': createdAt,
      'updatedAt': updatedAt,
      'updatedBy': updatedBy,
      'createdBy': createdBy,
    };
  }
}
