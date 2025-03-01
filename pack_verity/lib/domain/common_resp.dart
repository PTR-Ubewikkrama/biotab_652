class CommonResponse {
  final String status;
  final String statusDescription;

  CommonResponse({required this.status, required this.statusDescription});

  factory CommonResponse.fromJson(Map<String, dynamic> json) {
    return CommonResponse(
      status: json['status'] ?? '',
      statusDescription: json['statusDescription'] ?? '',
    );
  }

  bool isSuccess() {
    return status == "S1000";
  }
}
