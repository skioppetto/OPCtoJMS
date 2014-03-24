OPCtoJMS
========

a bridge from an OPC-DA server to a JMS queue. 
The target is to read realtimes changes from an OPC server and decouple elaboration.

Initial Import.

- OPCProducer: 
produce OPCMessage objects listening to OPC-DA server changes. Configuration in made of a context where
are define groups and triggers. When a trigger change all group items are read and send to a blocking queue.

- ConnectionInformationFactory: 
build a JIInterop ConnectionInformation starting from a context object

- OPCMessageAdapter: 
adapt an JIInterop object to a simple serializable POJO. This class is tested

What comes next.

In the next releases will be 
- test OPCProducer using as source a local hosted Matrikon OPC Server and evaluating messages put into the Blocking Queue.
- develop OPCConsumer to write OPC items reading requests from a Blocking Queue
- test OPCConsumer using as destination a local hosted Matrikon OPC Server
- develop JMSConsumer that read messages from a Blocking Queue and send them to a JMS queue
- test JMSConsumer 
- develop JMSProducer that listen to a JMS queue and send write requestes to a Blocking Queue
- test JMSProducer


