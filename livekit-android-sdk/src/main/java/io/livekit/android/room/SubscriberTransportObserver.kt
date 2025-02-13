package io.livekit.android.room

import com.github.ajalt.timberkt.Timber
import livekit.LivekitRtc
import org.webrtc.*

/**
 * @suppress
 */
class SubscriberTransportObserver(
    private val engine: RTCEngine
) : PeerConnection.Observer {


    override fun onIceCandidate(candidate: IceCandidate) {
        Timber.v { "onIceCandidate: $candidate" }
        engine.client.sendCandidate(candidate, LivekitRtc.SignalTarget.SUBSCRIBER)
    }

    override fun onAddTrack(receiver: RtpReceiver, streams: Array<out MediaStream>) {
        val track = receiver.track() ?: return
        Timber.v { "onAddTrack: ${track.kind()}, ${track.id()}, ${streams.fold("") { sum, it -> "$sum, $it" }}" }
        engine.listener?.onAddTrack(track, streams)
    }

    override fun onTrack(transceiver: RtpTransceiver) {
        when (transceiver.mediaType) {
            MediaStreamTrack.MediaType.MEDIA_TYPE_AUDIO -> Timber.v { "peerconn started receiving audio" }
            MediaStreamTrack.MediaType.MEDIA_TYPE_VIDEO -> Timber.v { "peerconn started receiving video" }
            else -> Timber.d { "peerconn started receiving unknown media type: ${transceiver.mediaType}" }
        }
    }

    override fun onDataChannel(channel: DataChannel) {
        Timber.v { "onDataChannel" }
    }

    override fun onStandardizedIceConnectionChange(newState: PeerConnection.IceConnectionState?) {
    }

    override fun onConnectionChange(newState: PeerConnection.PeerConnectionState?) {
        Timber.v { "onConnectionChange new state: $newState" }
    }

    override fun onSelectedCandidatePairChanged(event: CandidatePairChangeEvent?) {
    }

    override fun onSignalingChange(p0: PeerConnection.SignalingState?) {
    }

    override fun onIceConnectionChange(p0: PeerConnection.IceConnectionState?) {
        Timber.v { "onIceConnection new state: $p0" }
    }

    override fun onIceConnectionReceivingChange(p0: Boolean) {
    }

    override fun onIceGatheringChange(p0: PeerConnection.IceGatheringState?) {
    }

    override fun onIceCandidatesRemoved(p0: Array<out IceCandidate>?) {
    }

    override fun onAddStream(p0: MediaStream?) {
    }

    override fun onRemoveStream(p0: MediaStream?) {
    }

    override fun onRenegotiationNeeded() {
    }

}