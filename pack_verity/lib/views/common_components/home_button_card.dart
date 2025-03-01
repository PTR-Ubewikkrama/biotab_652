import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class HomeOperationsButtonData {
  final Color startColor;
  final Color endColor;
  final String mainText;
  final String desc;
  final Icon icon;
  final Function() function;

  HomeOperationsButtonData(this.startColor, this.endColor, this.mainText,
      this.desc, this.icon, this.function);
}

class HomeOperationsButton extends StatelessWidget {
  final HomeOperationsButtonData data;
  const HomeOperationsButton({Key? key, required this.data}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    double screenHeight = MediaQuery.of(context).size.height;

    return Padding(
        padding: const EdgeInsets.all(8.0),
        child: Container(
          decoration: BoxDecoration(
            gradient: const LinearGradient(
              begin: Alignment.centerLeft,
              end: Alignment.centerRight,
              colors: [
                Colors.white,
                Colors.white
              ], // Replace with your desired colors
            ),
            borderRadius: BorderRadius.circular(20.0),
            boxShadow: [
              BoxShadow(
                color: Colors.grey.withOpacity(0.5),
                spreadRadius: 3,
                blurRadius: 10,
                offset: const Offset(0, 0.5), // changes position of shadow
              ),
            ],
          ),
          height: screenHeight / 6,
          child: CupertinoButton(
            padding: const EdgeInsets.all(0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                const Expanded(
                  flex: 1,
                  child: SizedBox.shrink(),
                ),
                Expanded(flex: 4, child: Container(child: data.icon)),
                Expanded(
                  flex: 1,
                  child: Text(
                    data.mainText,
                    style: const TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 16,
                        color: Colors.black),
                  ),
                ),
                const SizedBox(
                  height: 3,
                ),
                Expanded(
                  flex: 1,
                  child: Text(
                    data.desc,
                    style: const TextStyle(fontSize: 11, color: Colors.grey),
                  ),
                ),
                const Expanded(
                  flex: 1,
                  child: SizedBox.shrink(),
                ),
              ],
            ),
            onPressed: () {
              data.function();
            },
          ),
        ));
  }
}
