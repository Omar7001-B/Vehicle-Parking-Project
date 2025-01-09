# Parking Area Manager

## Course Project Statement
**Vehicle Parking Problem - Level 2**

This project was developed as part of a course requirement to create a Java program for managing a parking area. The system handles a 100-meter parking space, dynamically allocating spots for vehicles based on their length and available space.

## Project Description

Parking Area Manager is a Java-based solution that efficiently manages a parking facility. The system intelligently handles space allocation, vehicle tracking, and fee calculation based on parking duration.

### Vehicle Types and Pricing
| Vehicle Type | Default Length | Hourly Rate |
|-------------|----------------|-------------|
| Truck       | 7m            | 15/hour     |
| Bus         | 10m           | 15/hour     |
| Car         | 5m            | 10/hour     |
| Motorcycle  | 2m            | 5/hour      |

### Key Features

- **Smart Space Allocation**: Automatically assigns parking spots based on vehicle length
- **Dynamic Space Management**: Merges adjacent free spaces for optimal space utilization
- **Vehicle Tracking**: Manages vehicles through unique IDs
- **Automated Billing**: Calculates parking fees based on duration and vehicle type
- **GUI Interface**: User-friendly interface with dark mode theme
- **Real-time Status**: Shows current parking occupancy and available spaces

## Usage

1. **Adding a Vehicle**:
   - Select "Add Vehicle" option
   - Enter vehicle type (truck/bus/car/motorcycle)
   - Provide vehicle ID
   - System will allocate appropriate space if available

2. **Releasing a Vehicle**:
   - Choose "Release Vehicle" option
   - Enter vehicle ID
   - Input parking duration
   - System calculates fee based on vehicle type and duration

3. **Viewing Status**:
   - Select "Show Status" to view:
     - Currently occupied spaces
     - Available parking spots
     - Space distribution

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Omar7001-B/Vehicle-Parking-Project.git
   ```
2. Navigate to the project directory
3. Run the application

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is open source and available under the MIT License.
