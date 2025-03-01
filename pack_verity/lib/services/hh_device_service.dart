import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:pack_verity/domain/common_resp.dart';
import 'package:pack_verity/domain/component_verification_resp.dart';
import 'package:pack_verity/domain/hh_device_resp.dart';
import 'package:pack_verity/domain/validate_component_resp.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/utils/keyBox.dart';

class HhDeviceService {
  final dio = sl.get<Dio>();

  Future<CommonResponse?> addHhDevice(Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$addHHDevicePath',
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

  Future<GetHHDeviceResponse?> getHhDevice(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$getHHDevicePath',
              data: {'code': code},
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return GetHHDeviceResponse.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return GetHHDeviceResponse(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return GetHHDeviceResponse(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return GetHHDeviceResponse(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<CommonResponse?> validateHHDeviceByCode(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateHHDevicePath',
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

  Future<ValidateComponentResp?> validateBatteryTest(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateBatteryTestCode',
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

  Future<ValidateComponentResp?> validateValveTest(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateValveTestCode',
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

  Future<CommonResponse?> deleteHhDeviceByCode(String code) async {
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
}
