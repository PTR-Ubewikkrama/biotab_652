import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:pack_verity/domain/common_resp.dart';
import 'package:pack_verity/domain/component_verification_resp.dart';
import 'package:pack_verity/domain/hh_device_resp.dart';
import 'package:pack_verity/domain/validate_component_resp.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/utils/keyBox.dart';

class BTDeviceService {
  final dio = sl.get<Dio>();

  Future<CommonResponse?> addBTDevice(Map<String, dynamic> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$addBTDevicePath',
              data: requestBody,
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return CommonResponse.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return CommonResponse(
            status: "E2000", statusDescription: 'Server Connection Timeout');
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return CommonResponse(
          status: "E2000", statusDescription: 'Something went wrong');
    } on Exception {
      return CommonResponse(
          status: "E2000", statusDescription: 'Something went wrong');
    }
    return null;
  }

  Future<GetBTDeviceResponse?> getBTDevice(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$getBTDevicePath',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return GetBTDeviceResponse.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return GetBTDeviceResponse(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return GetBTDeviceResponse(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return GetBTDeviceResponse(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<CommonResponse?> validateBTDeviceByCode(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateBTDevicePath',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      print(response.data);

      if (data is Map<String, dynamic>) {
        return CommonResponse.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return CommonResponse(
            status: "E2000", statusDescription: 'Server Connection Timeout');
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return CommonResponse(
          status: "E2000", statusDescription: 'Something went wrong');
    } on Exception {
      return CommonResponse(
          status: "E2000", statusDescription: 'Something went wrong');
    }
    return null;
  }

  Future<ComponentVerificationResp?> verifyComponent(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$componentVerificationPath',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return ComponentVerificationResp.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return ComponentVerificationResp(
            status: "E2000", statusDescription: 'Server Connection Timeout');
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return ComponentVerificationResp(
          status: "E2000", statusDescription: 'Something went wrong');
    } on Exception {
      return ComponentVerificationResp(
          status: "E2000", statusDescription: 'Something went wrong');
    }
    return null;
  }

  Future<ValidateComponentResp?> validatePcbTest(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);
    print(code);

    try {
      final response = await dio
          .post('$baseUrl$validatePcbTestCode',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      print(response.data);

      if (data is Map<String, dynamic>) {
        return ValidateComponentResp.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return ValidateComponentResp(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<ValidateComponentResp?> validateAirPumpTest(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateAirPumpTestCode',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return ValidateComponentResp.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return ValidateComponentResp(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<ValidateComponentResp?> validateFanTest(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateFanTestCode',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return ValidateComponentResp.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return ValidateComponentResp(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<ValidateComponentResp?> validateManiFoldTest(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateManiFoldTestCode',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return ValidateComponentResp.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return ValidateComponentResp(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<ValidateComponentResp?> validateLatchButtonTest(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateLatchButtonTestCode',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return ValidateComponentResp.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return ValidateComponentResp(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<ValidateComponentResp?> validateOverPressureValveTest(
      String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateOverPressureValveTestCode',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return ValidateComponentResp.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return ValidateComponentResp(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<ValidateComponentResp?> validatePowerSupplyTest(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validatePowerSupplyTestCode',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return ValidateComponentResp.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return ValidateComponentResp(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<ValidateComponentResp?> validateValveCardTest(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateValveCardTestCode',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return ValidateComponentResp.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return ValidateComponentResp(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<CommonResponse?> deleteBTDeviceByCode(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$deleteHHDeviceByCodePath',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      print(response.data);

      if (data is Map<String, dynamic>) {
        return CommonResponse.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return CommonResponse(
            status: "E2000", statusDescription: 'Server Connection Timeout');
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return CommonResponse(
          status: "E2000", statusDescription: 'Something went wrong');
    } on Exception {
      return CommonResponse(
          status: "E2000", statusDescription: 'Something went wrong');
    }
    return null;
  }

  Future<ValidateComponentResp?> validateUIPcbTest(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateUIPcbTestCode',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return ValidateComponentResp.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return ValidateComponentResp(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<ValidateComponentResp?> validateFrontBracketAssemblyTest(
      String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateFrontBracketAssemblyPath',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return ValidateComponentResp.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return ValidateComponentResp(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return ValidateComponentResp(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }
}
