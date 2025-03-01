import 'package:dio/dio.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get_it/get_it.dart';
import 'package:pack_verity/services/auth_service.dart';
import 'package:pack_verity/services/fa_service.dart';
import 'package:pack_verity/services/hh_device_service.dart';

GetIt sl = GetIt.instance;

void initialize() {
  sl.registerSingleton<Dio>(Dio());
  sl.registerSingleton<FlutterSecureStorage>(const FlutterSecureStorage());
  sl.registerSingleton<AuthService>(AuthService());
  sl.registerSingleton<HhDeviceService>(HhDeviceService());
  sl.registerSingleton<FAService>(FAService());
}
