<p align="center">
    <img width=100% src="/assets/cover.png">
  </a>
</p>
<p align="center"> 🤖 <b> RUNIC: Android tamper detection demo 📱 </b> </p>

<br>

Welcome to RUNIC, an Android tamper detection demo! RUNIC is designed to serve as a parallel and introductory tool for understanding more complex tamper detection and integrity systems such as [Google Play SafetyNet](https://developer.android.com/training/safetynet) and [Huawei Safety Detect](https://developer.huawei.com/consumer/en/hms/huawei-safetydetectkit/). By exploring the inner workings of RUNIC, you can gain insights into the fundamental concepts and techniques employed in these advanced solutions.

RUNIC utilizes a client/server model where the application collects device variables and sends them to a server for processing. The server then provides a percentage score indicating the likelihood of the device being tampered with. This repository is the successor to the previous tool, [Tamper](https://github.com/user1342/Tamper), and is currently in a minimal viable product (MVP) state. If you have any specific improvements, issues, or feature requests, please use the [issues tab](https://github.com/user1342/RUNIC/issues).

# ➡️ Getting Started
## Installation
RUNIC is an Android project based on Gradle, and it can be built using either Gradle directly or Android Studio. If you simply want to test the application, you can also check out the pre-built [APK](https://github.com/user1342/RUNIC/tree/main/artifacts).

## Running
RUNIC utilizes Alarm Managers and JobSchedulers to run checks in the background while the application is installed. The main activity of RUNIC includes a settings page to configure its behavior, but please note that these settings are not currently enabled. As a result, RUNIC runs periodically and on device boot.

<br>
<p align="center">
  <img src="/assets/demo.gif" width="200" />
  <img src="/assets/app.png" width="200" />
</p>
<br>
<p align="center">
  <img src="/assets/notification.png" width="200" /
</p>
<br>

# 📱 On Device
RUNIC gathers a collection of data from an Android device and sends it to the server for processing. After processing, RUNIC receives a score out of 100 indicating the integrity of the device. The data collected includes:

- Android Secure ID
- Application installer ID
- Boot state
- Verify mode
- Security patch level
- OEM unlock status
- Product brand
- Product model
- OEM unlock supported
- Debuggable status
- Application signature
- Emulator status
- Fingerprint status
- Storage encryption status
- Non-market apps enabled
- ADB enabled
- Lockscreen timeout
- Lock screen type
- Notification visibility
- Application permissions of installed apps

  

# 🌐 Server
The application component of RUNIC communicates with a [server](https://huggingface.co/spaces/User1342/RUNIC) for data processing. The server calculates a confidence score on the device integrity based on the received data. The server is also open source and hosted on [Hugging Face](https://huggingface.co/spaces/User1342/RUNIC). Data is stored indefinitely on the server and can be downloaded via the server UI.
  
<br>
<p align="center">
  <img src="/assets/server.png" width="750" /
</p>
<br>
  
The server performs the following checks:
- Debugging status
- Emulator detection
- Installation source (Google Play store)
- Boot state and verification status
- Previous log of the device and matching application certificates
- Comparison of variables between previous sightings of the application/ device

# 🙏 Contributions
RUNIC is an open-source project and welcomes contributions from the community. If you would like to contribute to RUNIC, please follow these guidelines:

- Fork the repository to your own GitHub account.
- Create a new branch with a descriptive name for your contribution.
- Make your changes and test them thoroughly.
- Submit a pull request to the main repository, including a detailed description of your changes and any relevant documentation.
- Wait for feedback from the maintainers and address any comments or suggestions (if any).
- Once your changes have been reviewed and approved, they will be merged into the main repository.

# ⚖️ Code of Conduct
RUNIC follows the Contributor Covenant Code of Conduct. Please make sure [to review](https://www.contributor-covenant.org/version/2/1/code_of_conduct/code_of_conduct.md). and adhere to this code of conduct when contributing to RUNIC.

# 🐛 Bug Reports and Feature Requests
If you encounter a bug or have a suggestion for a new feature, please open an issue in the GitHub repository. Please provide as much detail as possible, including steps to reproduce the issue or a clear description of the proposed feature. Your feedback is valuable and will help improve RUNIC for everyone.

# 📜 License
[GNU General Public License v3.0](https://choosealicense.com/licenses/gpl-3.0/)
