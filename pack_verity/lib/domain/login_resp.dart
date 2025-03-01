class LoginResponse {
  final String status;
  final String statusDescription;
  final AuthResponse data;

  LoginResponse(
      {required this.status,
      required this.statusDescription,
      required this.data});

  factory LoginResponse.fromJson(Map<String, dynamic> json) {
    return LoginResponse(
      status: json['status'] ?? '',
      statusDescription: json['statusDescription'] ?? '',
      data: AuthResponse.fromJson(json['data']),
    );
  }

  bool isSuccess() {
    return status == "S1000";
  }
}

class AuthResponse {
  final String token;
  final String username;
  final String group;
  final List<String> roles;

  AuthResponse(
      {required this.token,
      required this.username,
      required this.group,
      required this.roles});

  factory AuthResponse.fromJson(Map<String, dynamic> json) {
    return AuthResponse(
      token: json['token'] ?? '',
      username: json['username'] ?? '',
      group: json['group'] ?? '',
      roles: json['roles'] != null ? List<String>.from(json['roles']) : [],
    );
  }

  bool isSuccess() {
    return token == "S1000";
  }
}
