import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:pack_verity/domain/cartoon_package_resp.dart';
import 'package:pack_verity/domain/common_resp.dart';
import 'package:pack_verity/domain/fa_resp.dart';
import 'package:pack_verity/domain/get_cartoon_box_uids_by_id_resp.dart';
import 'package:pack_verity/domain/validate_device_for_stage_two_resp.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/utils/keyBox.dart';

class FAService {
  final dio = sl.get<Dio>();

  Future<CommonResponse?> validateDeviceById(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateDeviceForFAPath',
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

  Future<ValidateDeviceForStageTwoResponse?> validateDeviceForStageTwoById(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateDeviceForStageTwoForFAPath',
              data: requestBody,
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return ValidateDeviceForStageTwoResponse.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return ValidateDeviceForStageTwoResponse(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return ValidateDeviceForStageTwoResponse(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return ValidateDeviceForStageTwoResponse(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<GetFAResponse?> getFinalAssemblyById(String code) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .get('$baseUrl$getFinalAssemblyByIdPath/$code',
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return GetFAResponse.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return GetFAResponse(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return GetFAResponse(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return GetFAResponse(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<CommonResponse?> addFinalAssemblyStepOne(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$addFAStepOnePath',
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

  Future<CommonResponse?> updateFinalAssemblyStepOne(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$updateFAStepOnePath',
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

  Future<CommonResponse?> validateBladderCode(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateBladderFAPath',
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

  Future<CommonResponse?> validateUPLNumber(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateUPLFAPath',
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

  Future<CommonResponse?> validateUPLNumberForStepThree(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateUPLForStageThreeFAPath',
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

  Future<CommonResponse?> addFinalAssemblyStepTwo(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$addFAStepTwoPath',
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

  Future<CommonResponse?> updateFinalAssemblyStepTwo(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$updateFAStepTwoPath',
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

  Future<CommonResponse?> validateUDINumber(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateUDIFAPath',
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

  Future<CommonResponse?> validateAdapterCode(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateAdapterFAPath',
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

  Future<CommonResponse?> addFinalAssemblyStepThree(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$addFAStepThreePath',
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

  Future<CommonResponse?> updateFinalAssemblyStepThree(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$updateFAStepThreePath',
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

  Future<CommonResponse?> validateCartoonBoxUDINumber(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateCartoonBoxUDIFAPath',
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

  Future<CommonResponse?> validateCartoonBoxCartoonNumber(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$validateCartoonBoxCartoonNumberPath',
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

  Future<CommonResponse?> addCartoonPackage(
      Map<String, dynamic> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$addCartoonPackageFAPath',
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

  Future<CommonResponse?> updateCartoonPackage(
      Map<String, dynamic> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$updateCartoonPackageFAPath',
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

  Future<GetCartoonPackageResponse?> getCartoonPackageByUDINumber(
      String udiNumber) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .get('$baseUrl$getCartoonPackageByUDIFAPath$udiNumber',
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return GetCartoonPackageResponse.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return GetCartoonPackageResponse(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return GetCartoonPackageResponse(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return GetCartoonPackageResponse(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }

  Future<GetCartoonPackageUIDsByIdResponse?> getCartoonPackageByCartoonNumber(
      Map<String, String> requestBody) async {
    String? jwt = await sl.get<FlutterSecureStorage>().read(key: jwtDB);

    try {
      final response = await dio
          .post('$baseUrl$getCartoonPackageByCartoonNumberFAPath',
              data: requestBody,
              options: Options(headers: {'Authorization': 'Bearer $jwt'}))
          .timeout(const Duration(seconds: 20));

      final data = response.data;

      if (data is Map<String, dynamic>) {
        return GetCartoonPackageUIDsByIdResponse.fromJson(data);
      }
    } on DioException catch (e) {
      if (e.type == DioExceptionType.connectionTimeout) {
        return GetCartoonPackageUIDsByIdResponse(
            status: "E2000",
            statusDescription: 'Server Connection Timeout',
            data: null);
      }
      if (kDebugMode) {
        print(e.error.toString());
      }
      return GetCartoonPackageUIDsByIdResponse(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    } on Exception {
      return GetCartoonPackageUIDsByIdResponse(
          status: "E2000",
          statusDescription: 'Something went wrong',
          data: null);
    }
    return null;
  }
}
