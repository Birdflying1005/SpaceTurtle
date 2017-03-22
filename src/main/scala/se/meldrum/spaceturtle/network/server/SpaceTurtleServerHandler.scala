package se.meldrum.spaceturtle.network.server

import java.nio.channels.SocketChannel

import com.typesafe.scalalogging.LazyLogging
import io.netty.buffer.ByteBuf
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.util.ReferenceCountUtil

import scala.util.{Failure, Success, Try}

class SpaceTurtleServerHandler extends SimpleChannelInboundHandler[SocketChannel] with LazyLogging {

  override def channelRead0(ctx: ChannelHandlerContext, msg: SocketChannel) =  ???

  override def channelRead(ctx: ChannelHandlerContext, msg: Any): Unit = {
    val buf = msg.asInstanceOf[ByteBuf]

    val received = Try {
      val data = buf.toString(io.netty.util.CharsetUtil.US_ASCII)
      logger.debug("Msg received: " + data)
    }

    received match {
      case Success(s) => ReferenceCountUtil.release(msg)
      case Failure(e) => logger.debug(e.toString)
    }
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable): Unit = {
    logger.debug(ctx.toString)
    ctx.close()
  }

}
