package com.bbc.balan.sergii.notification.sns;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreatePlatformApplicationRequest;
import com.amazonaws.services.sns.model.CreatePlatformApplicationResult;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.DeletePlatformApplicationRequest;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class SNSAndroidMobile {
	
	private final AmazonSNS snsClient;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	
	public static final Map<NotificationPlatform, Map<String, MessageAttributeValue>> attributesMap = new HashMap<NotificationPlatform, Map<String, MessageAttributeValue>>();
	static {
		attributesMap.put(NotificationPlatform.ADM, null);
		attributesMap.put(NotificationPlatform.GCM, null);
		attributesMap.put(NotificationPlatform.APNS, null);
		attributesMap.put(NotificationPlatform.APNS_SANDBOX, null);
//		attributesMap.put(NotificationPlatform.BAIDU, addBaiduNotificationAttributes());
//		attributesMap.put(Platform.WNS, addWNSNotificationAttributes());
//		attributesMap.put(NotificationPlatform.MPNS, addMPNSNotificationAttributes());
	}
	
	
	public SNSAndroidMobile(AmazonSNS snsClient) {
		this.snsClient = snsClient;
	}
	
	public void demoAndroidAppNotification() {
		// TODO: Please fill in following values for your application. You can
		// also change the notification payload as per your preferences using
		// the method
		// com.amazonaws.sns.samples.tools.SampleMessageGenerator.getSampleAndroidMessage()
		String serverAPIKey = "AIzaSyCrkmZMYPhyu6To76cVcjTzWKtrwBcC7gk";
		String applicationName = "845022750047"; //Project ID: feisty-truth-91513 Project Number: 845022750047
		String registrationId = "APA91bG0krFWNVIKGFJMjaEXExhlGhb7Nt1m3ZdSEeBYiY9xBHVltHG99qOBxRRiqJr6zxGTaUoecJqIuCo2vmkM85K51I7EFKtpV4W7jWsjI8nqGy3p_RXQaUeoCqGZAjP6TJopWBGCaDWiZuLwgpdxcdGhTo-z5A";
		
		demoNotification("", serverAPIKey, registrationId, applicationName, attributesMap);
	}
	
	
	public void demoNotification(String principal, String credential, String platformToken, String applicationName, Map<NotificationPlatform, Map<String, MessageAttributeValue>> attrsMap) {
		
		// Create Platform Application. This corresponds to an app on a
		// platform.
		CreatePlatformApplicationResult platformApplicationResult = createPlatformApplication(applicationName, principal, credential);
		System.out.println(platformApplicationResult);

		// The Platform Application Arn can be used to uniquely identify the
		// Platform Application.
		String platformApplicationArn = platformApplicationResult.getPlatformApplicationArn();

		// Create an Endpoint. This corresponds to an app on a device.
		CreatePlatformEndpointResult platformEndpointResult = createPlatformEndpoint("CustomData - Useful to store endpoint specific data", platformToken, platformApplicationArn);
		System.out.println(platformEndpointResult);

		// Publish a push notification to an Endpoint.
		PublishResult publishResult = publish(platformEndpointResult.getEndpointArn());
		System.out.println("Published! \n{MessageId=" + publishResult.getMessageId() + "}");
		
		// Delete the Platform Application since we will no longer be using it.
		deletePlatformApplication(platformApplicationArn);
	}
	
	private CreatePlatformApplicationResult createPlatformApplication(String applicationName, String principal, String credential) {
		CreatePlatformApplicationRequest platformApplicationRequest = new CreatePlatformApplicationRequest();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("PlatformPrincipal", principal);
		attributes.put("PlatformCredential", credential);
		platformApplicationRequest.setAttributes(attributes);
		platformApplicationRequest.setName(applicationName);
		platformApplicationRequest.setPlatform(NotificationPlatform.GCM.toString());
		return snsClient.createPlatformApplication(platformApplicationRequest);
	}
	
	private CreatePlatformEndpointResult createPlatformEndpoint( String customData, String platformToken, String applicationArn) {
		CreatePlatformEndpointRequest platformEndpointRequest = new CreatePlatformEndpointRequest();
		platformEndpointRequest.setCustomUserData(customData);
		String token = platformToken;
		platformEndpointRequest.setToken(token);
		platformEndpointRequest.setPlatformApplicationArn(applicationArn);
		return snsClient.createPlatformEndpoint(platformEndpointRequest);
	}	
	
	private PublishResult publish(String endpointArn) {
		PublishRequest publishRequest = new PublishRequest();
		Map<String, MessageAttributeValue> notificationAttributes = new HashMap<String, MessageAttributeValue>();
		if (notificationAttributes != null && !notificationAttributes.isEmpty()) {
			publishRequest.setMessageAttributes(notificationAttributes);
		}
		publishRequest.setMessageStructure("json");
		// If the message attributes are not set in the requisite method,
		// notification is sent with default attributes
		String message = getSampleAndroidMessage();
		Map<String, String> messageMap = new HashMap<String, String>();
		messageMap.put(NotificationPlatform.GCM.toString(), message);
		message = jsonify(messageMap);
		// For direct publish to mobile end points, topicArn is not relevant.
		publishRequest.setTargetArn(endpointArn);

		// Display the message that will be sent to the endpoint/
		System.out.println("{Message Body: " + message + "}");
		StringBuilder builder = new StringBuilder();
		builder.append("{Message Attributes: ");
		for (Map.Entry<String, MessageAttributeValue> entry : notificationAttributes
				.entrySet()) {
			builder.append("(\"" + entry.getKey() + "\": \""
					+ entry.getValue().getStringValue() + "\"),");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append("}");
		System.out.println(builder.toString());

		publishRequest.setMessage(message);
		return snsClient.publish(publishRequest);
	}
	
	private void deletePlatformApplication(String applicationArn) {
		DeletePlatformApplicationRequest request = new DeletePlatformApplicationRequest();
		request.setPlatformApplicationArn(applicationArn);
		snsClient.deletePlatformApplication(request);
	}	
	
	public static String getSampleAndroidMessage() {
		Map<String, Object> androidMessageMap = new HashMap<String, Object>();
		androidMessageMap.put("collapse_key", "Welcome");
		androidMessageMap.put("data", getData());
		androidMessageMap.put("delay_while_idle", true);
		androidMessageMap.put("time_to_live", 125);
		androidMessageMap.put("dry_run", false);
		return jsonify(androidMessageMap);
	}
	
	private static Map<String, String> getData() {
		Map<String, String> payload = new HashMap<String, String>();
		payload.put("message", "Hello World!");
		return payload;
	}	
	
	public static String jsonify(Object message) {
		try {
			return objectMapper.writeValueAsString(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw (RuntimeException) e;
		}
	}	
	
}
