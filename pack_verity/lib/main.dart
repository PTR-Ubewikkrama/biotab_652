import 'package:flutter/material.dart';
import 'package:pack_verity/service_locator.dart';
import 'package:pack_verity/views/layout/layout.dart';

void main() {
  initialize();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Demo',
        debugShowCheckedModeBanner: false,
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
          useMaterial3: true,
        ),
        initialRoute: '/',
        routes: {
          '/': (context) => const LayoutPage(),
          '/home': (context) => const LayoutPage(),
        });
  }
}
