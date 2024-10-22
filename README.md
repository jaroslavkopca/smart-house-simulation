# Smart Home Project

## Overview
The Smart Home Project is a Java-based simulation modeling the interactions within an intelligent home environment. This project represents the design of Java programming and showcases various design patterns and system interactions.

## Team Members
- Matyas Urban
- Jaroslav Kopca

## Project Summary
The simulation encompasses a range of entities, including devices, sensors, people, pets, and structural components like rooms and floors. 

### Key Components
- **House Class**: The primary container linking multiple Floor instances, which aggregate Room objects.
- **Room Types**: Each room is classified by RoomType and can contain objects such as Device, Sensor, and Pet.
- **Person Instances**: Represent individuals who move between rooms and interact with devices.

### Device Hierarchy
Specific devices such as Coffee Maker, Washing Machine, and Air Purifier inherit from the Device class, allowing for specialized behaviors.

### Controller Class
The Controller class manages time and events, generating reports via methods like `HouseConfigurationReport()` and `ConsumptionReport()`. 

### Action Class
The Action class, associated with the Controller, represents tasks triggered by various actors within the system.

### Use Case Diagram
Interactions in the system include:
- Go to Sleep
- Drink Coffee
- Start Washing

### External Triggers
The Time class acts as an external trigger for events like Change Date/Night, while the Pet class has use cases like Eat from Feeder.

## Design Patterns
The following design patterns are strategically chosen to structure the system efficiently:
- **Composite**: Represents the hierarchical structure of the home.
- **Memento**: Captures the state of objects for historical tracking and rollback capabilities.
- **Strategy**: Allows interchangeable algorithms within devices, enhancing flexibility.
- **Command**: Encapsulates requests as objects, decoupling the sender from the performer.
- **State**: Manages state-dependent behavior of devices, enabling a clean transition between states.
- **Observer**: Keeps the system components synchronized with state changes.
- **Singleton**: Ensures a single point of control and coordination, likely applied to the Controller.
- **Factory Method**: Provides a mechanism for creating instances of devices, allowing for extensibility.

## Conclusion
This project showcases the capabilities of Java programming in simulating an intelligent home environment and demonstrates effective design principles and patterns for managing complex interactions among various components.
