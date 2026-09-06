# Management Assets Core

Core backend for a university asset management system, designed to
manage facilities, equipment, inventory, and the complete lifecycle of
institutional assets.

## Overview

The system supports large-scale asset management in a university
environment, covering asset registration, allocation, handover,
inventory, maintenance, status tracking, and disposal.

The system was designed around standardized and traceable management
workflows, with clear responsibilities and asset lifecycle control.

## Key Features

-   Asset and equipment management
-   Facilities and infrastructure management
-   Asset allocation and handover
-   Inventory and periodic asset verification
-   Asset lifecycle and status tracking
-   Maintenance and repair management
-   Asset transfer and disposal workflows
-   Organizational unit and responsibility management
-   Reporting and asset statistics
-   Role-based access control

## Public Asset Management

The system was developed with reference to Vietnam's regulatory
framework for the management and use of public assets, including:

-   **Law No. 15/2017/QH14** on Management and Use of Public Property
-   **Decree No. 151/2017/NĐ-CP** and its amendments on implementation
    of the Law
-   Relevant regulations and guidance issued by the **Ministry of
    Finance**

The system translates these management requirements into structured
digital workflows for asset registration, utilization, inventory,
transfer, maintenance, and disposal.

## ISO-Aligned Process Design

The system follows process-oriented principles aligned with ISO-style
quality management practices:

-   Standardized workflows
-   Clear responsibility and authorization
-   Data consistency
-   Operational traceability
-   Controlled asset lifecycle
-   Auditable transaction history

This design supports transparent and consistent asset management rather
than claiming formal ISO certification.

## Architecture & Design Patterns

The backend follows a layered architecture:

``` text
Controller
    │
    ▼
Service
    │
    ▼
Business / Factory Layer
    │
    ▼
Repository
    │
    ▼
Database
```

### Factory Pattern

The **Factory Design Pattern** is applied to handle different asset
types and asset-processing workflows.

``` text
                Asset Factory
                     │
        ┌────────────┼────────────┐
        ▼            ▼            ▼
   Equipment     Facility     Other Asset
        │            │            │
        └────────────┼────────────┘
                     ▼
              Asset Lifecycle
```

Instead of coupling business services directly to specific asset
implementations, the Factory layer selects and creates the appropriate
processing component according to asset type and operational context.

This approach improves:

-   Extensibility
-   Separation of concerns
-   Maintainability
-   Reusability
-   Consistency of asset-processing rules

## Asset Lifecycle

``` text
Registration
     ↓
Allocation
     ↓
Usage
     ↓
Inventory
     ↓
Maintenance / Transfer
     ↓
Recovery / Disposal
```

The lifecycle model provides centralized tracking of an asset from its
initial registration through its operational use and final disposition.

## Technology Stack

`Java` · `Spring Boot` · `Spring Security` · `JPA / Hibernate` ·
`REST API` · `MySQL / MariaDB` · `Maven`

## Engineering Focus

-   Enterprise Backend Development
-   Public Asset Management
-   Factory Design Pattern
-   Business Process Modeling
-   Asset Lifecycle Management
-   RESTful API Design
-   Role-Based Access Control
-   Transaction Management
-   Large-Scale University Information Systems

## Project History

The system was developed for university asset and facilities management
and evolved through long-term operational development.

The repository was later migrated from GitLab to GitHub while preserving
its development history.
