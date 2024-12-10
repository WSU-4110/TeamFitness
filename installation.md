# Installation Guide

## System Requirements

To build and run the TeamFitness application, ensure your system meets the following requirements:

- **Operating System**: Windows 10 or later, macOS 10.15 or later, or a recent Linux distribution.
- **Java Development Kit (JDK)**: JDK 11 or higher.
- **Android Studio**: Version 4.0 or higher.
- **Android SDK**: API Level 21 (Lollipop) or higher.
- **Gradle**: Version 6.5 or higher.
- **Internet Connection**: Required for downloading dependencies and libraries.

## Prerequisites

Before proceeding with the installation, ensure the following software is installed:

1. **Java Development Kit (JDK)**:
   - Download and install the latest JDK from the [Oracle website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

2. **Android Studio**:
   - Download and install Android Studio from the [official website](https://developer.android.com/studio).

3. **Android SDK**:
   - Within Android Studio, use the SDK Manager to install the necessary SDK platforms and tools.

4. **Gradle**:
   - Gradle is included with Android Studio. Ensure it's updated to the required version via the SDK Manager.

## Building the Application

Follow these steps to build the TeamFitness application:

1. **Clone the Repository**:
   - Open a terminal or command prompt.
   - Execute the following command to clone the repository:
     ```bash
     git clone https://github.com/WSU-4110/TeamFitness.git
     ```
   - Navigate to the project directory:
     ```bash
     cd TeamFitness
     ```

2. **Open the Project in Android Studio**:
   - Launch Android Studio.
   - Select "Open an existing project" and navigate to the `TeamFitness` directory.
   - Allow Android Studio to sync the project and download necessary dependencies.

3. **Configure the Project**:
   - Ensure that the correct JDK and SDK paths are set in Android Studio's settings.
   - Verify that the `build.gradle` files are correctly configured.

4. **Build the Project**:
   - In Android Studio, select "Build" from the top menu.
   - Click on "Make Project" or press `Ctrl+F9` to compile the project.

## Running the Application

After successfully building the project:

1. **Set Up an Emulator or Physical Device**:
   - **Emulator**: Use the AVD Manager in Android Studio to create and start a virtual device.
   - **Physical Device**: Connect your Android device via USB and ensure USB debugging is enabled.

2. **Run the Application**:
   - In Android Studio, click the "Run" button or press `Shift+F10`.
   - Select the target device (emulator or physical) to deploy the application.

## Troubleshooting

- **Dependency Issues**: If you encounter errors related to missing dependencies, try syncing the project with Gradle files by clicking "Sync Project with Gradle Files" in Android Studio.
- **Emulator Performance**: For better performance, enable hardware acceleration for the Android emulator. Consider downgrading the version of Android if you are running the emulator on an older computer.
- **Build Failures**: Check the "Build" window in Android Studio for detailed error messages and address them accordingly.

For further assistance, refer to the [official Android Studio documentation](https://developer.android.com/studio) or consult the project's [GitHub Issues page](https://github.com/WSU-4110/TeamFitness/issues).
