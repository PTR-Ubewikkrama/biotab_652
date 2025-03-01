import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:pack_verity/domain/login_resp.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/utils/keyBox.dart';

class AuthService {
  final dio = sl.get<Dio>();

  Future<LoginResponse?> login(String username, String password) async {
    LoginResponse? loginResp;
    try {
      final response = await dio.post('$baseUrl$loginPath', data: {
        'email': username,
        'password': password
      }).timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        loginResp = LoginResponse.fromJson(data);
      }

      if (loginResp!.isSuccess()) {
        await sl
            .get<FlutterSecureStorage>()
            .write(key: jwtDB, value: loginResp.data.token);
        await sl
            .get<FlutterSecureStorage>()
            .write(key: userGroupDB, value: loginResp.data.group);
        await sl
            .get<FlutterSecureStorage>()
            .write(key: userNameDB, value: loginResp.data.username);
      } else if (loginResp.status == "E3000") {
        await sl
            .get<FlutterSecureStorage>()
            .write(key: resetToken, value: loginResp.data.token);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        loginResp = LoginResponse(
            status: "E2000",
            statusDescription: 'Connection Timeout',
            data: AuthResponse(
                token: '', username: '', group: '', roles: <String>[]));
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
    }
    return loginResp;
  }

  Future<void> logout() async {
    await sl.get<FlutterSecureStorage>().delete(key: jwtDB);
    await sl.get<FlutterSecureStorage>().delete(key: userGroupDB);
    await sl.get<FlutterSecureStorage>().delete(key: userNameDB);
  }

  Future<LoginResponse?> validateJWT() async {
    LoginResponse? loginResp;
    return sl.get<FlutterSecureStorage>().read(key: jwtDB).then((jwt) async {
      if (jwt == null) {
        return LoginResponse(
            status: "E2000",
            statusDescription: 'No JWT',
            data: AuthResponse(
                token: '', username: '', group: '', roles: <String>[]));
      }
      try {
        final response = await dio.get('$baseUrl$validatePath',
            options: Options(headers: {'Authorization': 'Bearer $jwt'}));

        final data = response.data;

        if (data is Map<String, dynamic>) {
          return LoginResponse.fromJson(data);
        }
      } on DioException catch (e) {
        if (e.type == DioExceptionType.connectionTimeout) {
          return LoginResponse(
              status: "E2000",
              statusDescription: 'Invalid JWT',
              data: AuthResponse(
                  token: '', username: '', group: '', roles: <String>[]));
        }
        if (kDebugMode) {
          print(e.error.toString());
        }
      }
      return LoginResponse(
          status: "E2000",
          statusDescription: 'Invalid JWT',
          data: AuthResponse(
              token: '', username: '', group: '', roles: <String>[]));
    });
  }

  resetPassword(String text) {}
}
