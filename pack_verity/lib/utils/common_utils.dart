import 'dart:math';

import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:pack_verity/service_locator.dart';

String generateUniqueString({int fixedLength = 22}) {
  // Get the current date and time
  DateTime now = DateTime.now();

  // Format the date and time as a string
  String formattedDateTime = now.toLocal().toString();

  // Remove spaces and special characters from the date/time string
  formattedDateTime =
      formattedDateTime.replaceAll(RegExp(r'[^0-9a-zA-Z]+'), '');

  // Calculate the length available for the random integer
  int availableLength = fixedLength - formattedDateTime.length;

  // Generate a random integer with leading zeros to achieve the fixed length
  int randomInt = Random().nextInt(pow(10, availableLength).toInt());

  // Pad the random integer with leading zeros
  String paddedRandomInt = randomInt.toString().padLeft(availableLength, '0');

  // Combine the formatted date/time and padded random integer to create a unique string
  String uniqueString = "$formattedDateTime$paddedRandomInt";

  return uniqueString;
}

List<String> splitString(String input) {
  List<String> parts = [];
  // Split the input string using '(' and ')' as delimiters
  List<String> splitList = input.split(RegExp(r'[()]'));

  // Filter out any empty strings
  splitList.removeWhere((element) => element.isEmpty);

  // Combine the split elements in pairs to get the final parts
  for (int i = 0; i < splitList.length; i += 2) {
    String part = '(${splitList[i]})${splitList[i + 1]}';
    parts.add(part);
  }

  return parts;
}

String getSNNo(String partOne, String partTwo, String partThree) {
  // Extract the desired substrings from the original parts
  String extractedPartTwo =
      partTwo.replaceAll(RegExp(r'[()]'), '').substring(2);
  String extractedPartThree = partThree
      .substring(0, partThree.length - 1)
      .replaceAll(RegExp(r'[()]'), '');

  // Concatenate the extracted substrings and the last character of partThree
  String finalString =
      "${partThree.substring(partThree.length - 1)}$extractedPartTwo$extractedPartThree";

  return finalString;
}

class SettingsVal {
  final double zoomVal;
  final bool flashVal;
  final double scaleVal;

  SettingsVal(
      {required this.flashVal, required this.scaleVal, required this.zoomVal});
}

Future<SettingsVal> getZoomVal(
    String zoomKey, String scaleKey, String flashKey) async {
  bool flashVal = bool.parse(
      await sl.get<FlutterSecureStorage>().read(key: flashKey) ?? "false");
  double zoolLevel = double.parse(
      await sl.get<FlutterSecureStorage>().read(key: zoomKey) ?? "0.0");
  double scanAreaVal = double.parse(
      await sl.get<FlutterSecureStorage>().read(key: scaleKey) ?? "0.0");
  return SettingsVal(
      flashVal: flashVal, scaleVal: scanAreaVal, zoomVal: zoolLevel);
}
