# 🚌 Java EventBus System 🚀
<img src="images/logo.png" width="256">
<p align="center">
	<a href="https://opensource.org/licenses/MIT">
		<img src="https://img.shields.io/github/license/seba4316/EventBus?style=for-the-badge" /></a>
    <a href="https://github.com/seba4316/EventBus" alt="Last Commit">
        <img src="https://img.shields.io/github/last-commit/seba4316/EventBus?style=for-the-badge" /></a>
</p>


Welcome to a powerful and efficient tool designed to simplify event-driven programming in Java. This system is perfect for developers looking to decouple different parts of their application, making it easier to maintain and extend.

## 🌟 Features

- **Annotation-Driven**: Utilize annotations to mark methods as event handlers with specified priorities.
- **Event Prioritization**: Control the order of event handler execution with predefined priority levels.

- **Lightweight & Efficient**: Optimized for minimal overhead and maximum performance.
- **Easy to Use**: Intuitive API that gets your event-driven system up and running in no time.
- **Flexible Listener Registration**: Dynamically register and unregister event listeners at runtime.
- **Support for Event Inheritance**: Handle events based on their type hierarchy, allowing for generic handlers.
- **Cancellable Events**: Gain full control over your events' lifecycle with cancellable events. This feature allows an event to be aborted before completion, ensuring that while the initiating action may not proceed, all listeners are still notified, enabling clean-up or logging activities.
- **Stopping Propagation**: Elevate your event management strategy with precise control over event flow. Events can be halted from further propagation with immediate effect, allowing for targeted handling and response to specific conditions or criteria.

## 🚀 Getting Started

### 📋 Prerequisites

Ensure you have Java JDK 8 or later installed on your machine.

### 🛠️ Dependencies

The EventBus relies deeply on [Java-BetterReflection](https://github.com/OxideWaveLength/Java-BetterReflection) for enhanced reflection capabilities, making event handling more dynamic and powerful.
### 🔧 Setup

1. **Include the EventBus**

```xml
<dependency>
    <groupId>dev.utano</groupId>
    <artifactId>EventBus</artifactId>
    <version>0.1</version>
</dependency>
```

2. **Include Java-BetterReflection** (dependency)

```xml
<!-- Java-BetterReflection -->
<dependency>
    <groupId>top.wavelength</groupId>
    <artifactId>Java-BetterReflection</artifactId>
    <version>1.2</version>
</dependency>
```

### 📚 Examples
**Get yourself started**, familiarize yourself with the fundamentals of EventBus through simple, yet illustrative examples in our [Documentation](https://utano.dev/EventBus/Introduction.html).

### 🛣️ Roadmap

The journey to make this EventBus the premier event handling system is ongoing, and outlined here are the key milestones we aim to achieve. Here's what's on the horizon:

- **🔊 Dead Events Handling**: Introducing a robust mechanism to manage events that find no handlers, ensuring they are not lost in the void but handled gracefully or logged for auditing purposes.
- **⚡ Performance Enhancements**: We're committed to making the EventBus not just functional but blazing fast. Upcoming updates focus on optimizing performance, reducing latency, and improving efficiency under high loads.
- **🌐 Asynchronous Event Handling**: Enhancing our EventBus with advanced support for asynchronous event processing, allowing for non-blocking event dispatch and handling. This feature aims to facilitate better scalability and responsiveness in applications that demand high concurrency.
- **🌍 Community-Driven Features**: We're listening to you! Based on community feedback, we'll be prioritizing new features and improvements that matter most to our users.

### 🚧 Beta

The EventBus is still in beta, therefore breaking changes are to be expected as features are added or refined.

### 🤝 Contributing

We welcome contributions to make the EventBus System even better! Feel free to fork the repository, make your changes, and submit a pull request.

### 📄 License
This project is licensed under the MIT License - see the LICENSE file for details.

Happy Coding! 🎉