package num.ic.sad.sadtemplate.customer;

// Хуучин системийн өгөгдлийн хэлбэр (нэршил нь манайхаас өөр)
public record ExternalCustomer(String cust_id_num, String raw_full_name,
                               String contact_email, String addr_blob, int vip_flag) {
}
