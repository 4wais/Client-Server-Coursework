# Client-Server-Coursework
API Overview

This project is a RESTful Smart Campus API developed using JAX-RS (Jersey) and Maven for managing Rooms, Sensors, and Sensor Readings within a university campus.

The university requires a scalable backend service for managing campus infrastructure such as:

temperature sensors
CO2 monitors
occupancy trackers
smart lighting systems

The API provides:

Room creation and deletion
Sensor registration linked to rooms
Sensor filtering using query parameters
Historical sensor reading management
Sub-resource nesting for readings
Custom exception handling using Exception Mappers
Request and response logging using JAX-RS filters

The API follows RESTful best practices by using:

resource-based URLs
proper HTTP methods (GET, POST, DELETE)
appropriate HTTP status codes
JSON request and response bodies
nested sub-resources
centralised exception handling

The project uses only:

JAX-RS
HashMap
ArrayList

How to Build and Run
1. Clone Repository - Use git clone https://github.com/4wais/Client-Server-Coursework/
2. Open in Netbeans - File -> Open Project
3. Reload Project - Right Click Project
4. Clean and Build - Right Click Project
5. Run Project
6. Test API - Use Postmans, etc with http://localhost:8080/api/v1/



README Coursework Answers

PART 1

Q1. JAX-RS Resource Lifecycle

By default, JAX-RS resource classes are instantiated per request,
meaning a new object is created for every incoming HTTP request.

This improves thread safety because requests do not share the same
resource instance. However, shared application data such as HashMaps or
ArrayLists cannot be stored as instance variables inside resource
classes because they would be recreated on every request.

To preserve data across requests, shared data must be stored using
static collections or singleton service classes.

Since multiple users may access the API simultaneously, thread-safe
structures such as ConcurrentHashMap should be used to prevent race
conditions, inconsistent updates, and data loss. This ensures that two
users updating the same room or sensor at the same time do not overwrite
each other’s changes. It also helps maintain consistent API behaviour
under heavy usage.

Q2. Why is HATEOAS important?

HATEOAS allows an API to return links inside responses so that clients
can dynamically discover available actions. This improves flexibility,
reduces hardcoded endpoints, and makes the API easier to maintain.

Clients do not need to memorise every endpoint because the server guides
them using links. This also makes future API updates easier because
clients can follow provided links instead of relying only on static
documentation.

PART 2

Q1. Returning IDs vs Full Objects

Returning only IDs reduces payload size and network bandwidth usage,
which improves performance for large datasets. However, the client must
make additional requests to fetch full details.

Returning full objects increases response size but reduces the number of
additional API calls required by the client. This improves convenience
for clients that need immediate access to all room details. A good API
design often balances both approaches depending on the use case.

Q2. Is DELETE idempotent?

Yes, DELETE is idempotent. Calling DELETE multiple times produces the
same final system state. The first request deletes the room, and later
requests may return 404, but the room remains deleted.

This is important because network issues may cause clients to retry the
same request accidentally. Idempotency ensures that repeated DELETE
operations do not create unexpected side effects.

PART 3

Q1. @Consumes(JSON)

The annotation @Consumes(MediaType.APPLICATION_JSON) tells JAX-RS that
the endpoint only accepts JSON input.

If a client sends text/plain or application/xml instead of JSON, JAX-RS
returns 415 Unsupported Media Type.

This protects the API from unsupported formats and ensures that request
bodies are parsed correctly. It also improves consistency because all
clients must communicate using the same format.

Q2. Why use QueryParam?

Using /api/v1/sensors?type=CO2 is better than /api/v1/sensors/type/CO2
because query parameters are specifically designed for filtering and
searching collections.

They are also easier to extend when multiple filters are needed, such as
type and status together. Path parameters are better for identifying a
single resource, while query parameters are better for optional
filtering.

PART 4

Q1. Benefits of Sub-Resource Locator Pattern

The Sub-Resource Locator pattern improves API structure by delegating
nested resources to dedicated classes. This improves separation of
concerns, maintainability, and testing.

Instead of placing all logic in one large controller, each class handles
only its own responsibility. This makes debugging easier and allows the
API to scale more cleanly as more nested resources are added.

Q2. Why update currentValue?

Updating currentValue ensures the latest sensor value is always
available without searching the full reading history. This improves
consistency and performance.

It also allows dashboards or monitoring systems to quickly display live
sensor values. Without this update, the API could return outdated
information and reduce trust in the system.

PART 5

Q1. Why 409 Conflict?

HTTP 409 Conflict is appropriate because deleting a room with active
sensors conflicts with the current system state.

The request itself is valid, but it cannot be completed safely because
it would leave orphaned sensor data behind. This makes 409 more accurate
than a generic bad request response.

Q2. Why 422 instead of 404?

HTTP 422 is more accurate because the endpoint exists and the JSON is
valid, but the referenced roomId inside the payload is invalid.

A 404 is normally used when the URL itself does not exist. In this case,
the problem is inside the request body, so 422 better explains the real
issue to the client.

Q3. Why 403 Forbidden?

HTTP 403 is used because the sensor exists, but posting readings is not
allowed while it is in MAINTENANCE mode.

This shows that the request is understood, but the server refuses to
allow the action because of business rules. It clearly communicates that
the restriction is intentional rather than a technical failure.

Q4. Risks of exposing stack traces

Stack traces reveal internal class names, package structure, framework
versions, and server details, which attackers can use to target
vulnerabilities.

This information can help attackers identify weak points and known
exploits for specific libraries. For security reasons, APIs should
return only generic error messages to users and keep full technical
details in server logs.

Q5. Why use filters for logging?

Filters centralise logging for all requests and responses, avoid
repeated code, and keep resource classes clean.

They also ensure that every endpoint is logged consistently without
developers forgetting to add logging manually. This improves
maintainability and helps administrators troubleshoot production issues
more effectively.
