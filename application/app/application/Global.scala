package application

import Services.{StreamProcessorImpl, StreamProcessor, StreamingServiceImpl, StreamingService}
import sindi.core._

object Global {
  implicit val streamingService = bind[StreamingService](new StreamingServiceImpl)
  implicit val streamingProcessor = bind[StreamProcessor](new StreamProcessorImpl)

}
