package com.geekhive.studentsoft.utils;

import com.geekhive.studentsoft.beans.OrdersAr.OrdersAr;
import com.geekhive.studentsoft.beans.addbehaviour.AddBehaviour;
import com.geekhive.studentsoft.beans.addfirebaseid.AddFirebaseID;
import com.geekhive.studentsoft.beans.addhomework.AddHomework;
import com.geekhive.studentsoft.beans.addonlinepayment.CreateOnlinePayment;
import com.geekhive.studentsoft.beans.addtocart.AddToCart;
import com.geekhive.studentsoft.beans.allcartitem.AllCartItem;
import com.geekhive.studentsoft.beans.applicationform.ApplicationForm;
import com.geekhive.studentsoft.beans.applyleave.ApplyLeave;
import com.geekhive.studentsoft.beans.applyleave.ApplyLeaveStudent;
import com.geekhive.studentsoft.beans.applyvecancy.ApplyVecancy;
import com.geekhive.studentsoft.beans.behaviourlist.BehaviourListResponce;
import com.geekhive.studentsoft.beans.callapplication.AcademicYear;
import com.geekhive.studentsoft.beans.callapplication.CallApplication;
import com.geekhive.studentsoft.beans.changepassword.ChangePasswordBean;
import com.geekhive.studentsoft.beans.confirmorder.ConfirmStatonaryOrder;
import com.geekhive.studentsoft.beans.createorder.CreateOrder;
import com.geekhive.studentsoft.beans.editbehaviour.EditBehaviour;
import com.geekhive.studentsoft.beans.edithomework.EditHomeWork;
import com.geekhive.studentsoft.beans.eventregistration.EventRegistration;
import com.geekhive.studentsoft.beans.feestructure.StudentFeeS;
import com.geekhive.studentsoft.beans.forgetpassword.ForGetPassword;
import com.geekhive.studentsoft.beans.getallapplication.GetAllApplication;
import com.geekhive.studentsoft.beans.getallbus.GetAllBus;
import com.geekhive.studentsoft.beans.getallclasses.GetAllClasses;
import com.geekhive.studentsoft.beans.getallevents.GetAllEvents;
import com.geekhive.studentsoft.beans.getallexam.GetAllExamlist;
import com.geekhive.studentsoft.beans.getallorder.GetAllOrders;
import com.geekhive.studentsoft.beans.getcartbyuser.GetCartByUser;
import com.geekhive.studentsoft.beans.getexamnames.GetExamNames;
import com.geekhive.studentsoft.beans.gethomework.GetHomeWork;
import com.geekhive.studentsoft.beans.getparentsbyid.GetParentsByID;
import com.geekhive.studentsoft.beans.getstudentbyid.GetStudentById;
import com.geekhive.studentsoft.beans.getstudenttimetable.GetStudentTimetable;
import com.geekhive.studentsoft.beans.getupcommingevents.GetUpcommingEvents;
import com.geekhive.studentsoft.beans.guestoparent.GuestParent;
import com.geekhive.studentsoft.beans.guestregstration.Guestregistration;
import com.geekhive.studentsoft.beans.holidaylist.HolidayList;
import com.geekhive.studentsoft.beans.homebanner.Homebanner;
import com.geekhive.studentsoft.beans.homework.Homework;
import com.geekhive.studentsoft.beans.homeworklistresponce.HomeworkListResponce;
import com.geekhive.studentsoft.beans.leaveapply.Dates;
import com.geekhive.studentsoft.beans.leaveapply.Leaveapply;
import com.geekhive.studentsoft.beans.leavecancel.Leavecancel;
import com.geekhive.studentsoft.beans.leaveimage.LeaveImage;
import com.geekhive.studentsoft.beans.loginout.LoginOut;
import com.geekhive.studentsoft.beans.nonteachingstaffbyid.NonTeachingStaffById;
import com.geekhive.studentsoft.beans.notice.NoticeData;
import com.geekhive.studentsoft.beans.notification.Notification;
import com.geekhive.studentsoft.beans.orderhistory.OrderHistory;
import com.geekhive.studentsoft.beans.pickupnotification.PickupNotification;
import com.geekhive.studentsoft.beans.postpayment.PostPaymentOnline;
import com.geekhive.studentsoft.beans.removeproductfromcart.RemoveProductFromCart;
import com.geekhive.studentsoft.beans.schoollist.SchoolList;
import com.geekhive.studentsoft.beans.seatvacancy.SeatVacancyResponce;
import com.geekhive.studentsoft.beans.sectionbyname.SectionByName;
import com.geekhive.studentsoft.beans.setcurrentlocation.CurrentLocation;
import com.geekhive.studentsoft.beans.staonaryorderhistory.StaonaryOrderHistory;
import com.geekhive.studentsoft.beans.stationarycategory.StationaryCategory;
import com.geekhive.studentsoft.beans.stonaryprouct.StonaryProduct;
import com.geekhive.studentsoft.beans.studentapplyleave.StudentleaveApply;
import com.geekhive.studentsoft.beans.studentfeeonline.StudentFeeOnline;
import com.geekhive.studentsoft.beans.studentlibrary.GetBookIssued;
import com.geekhive.studentsoft.beans.teacherbyid.TeacherByID;
import com.geekhive.studentsoft.beans.teacherpresentattendance.TeacherAttendance;
import com.geekhive.studentsoft.beans.teachertimetable.TeacherTimetable;
import com.geekhive.studentsoft.beans.trackloc.UpdateCurrentLocation;
import com.geekhive.studentsoft.beans.usercurrentlocation.UserLocation;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitApi {


    @POST("auth/login")
    @FormUrlEncoded
    Call<LoginOut> login(@Field("email") String email, @Field("password") String password, @Field("key") String key);

    @POST("auth/forgot_password")
    @FormUrlEncoded
    Call<ForGetPassword> forgetpassword(@Field("email") String email, @Field("key") String key);



    @POST("auth/change_password/{id}")
    @FormUrlEncoded
    Call<ChangePasswordBean> changepassword(@Path("id") String id, @Field("old_password") String old_password, @Field("new_password") String new_password,@Header("db_key") String db_key);

    @GET("timetable/teacher/{id}")
    Call<TeacherTimetable> teachertimetable(@Path("id") String id,@Header("db_key") String db_key);

    @POST("attendance/teacher/present/{id}")
    Call<TeacherAttendance>teacherattendance(@Path("id") String id,@Header("db_key") String db_key);


    @POST("attendance/teacher/leave/{id}")
    Call<Leaveapply>applyleave(@Path("id") String id, @Body ApplyLeave applyLeave,@Header("db_key") String db_key);

    @GET("school/holidays")
    Call<HolidayList> holiday(@Header("db_key") String db_key);

    @POST("homework")
    Call<AddHomework>homework(@Body Homework homework,@Header("db_key") String db_key);

    @POST("homework/edit")
    Call<EditHomeWork>edithomework(@Body Homework homework,@Header("db_key") String db_key);


    @GET("teacher/byId/{id}")
    Call<TeacherByID> teacherbyid(@Path("id") String id,@Header("db_key") String db_key);

    @POST("homework/subject")
    @FormUrlEncoded
    Call<HomeworkListResponce> homeworklist(@Field("class") String clas, @Field("section") String section, @Field("subject") String subject,@Header("db_key") String db_key);

    @POST("teacher/student/behaviour_note")
    @FormUrlEncoded
    Call<AddBehaviour>addbehaviour(@Field("student_id") String student_id,@Field("teacher_id") String teacher_id,@Field("title") String title,@Field("details") String details,
                                   @Field("subject_name") String subject_name,@Header("db_key") String db_key);


   /* @GET("section/byName/{clas}/{section}")
    Call<GetSectionbyID> sectionbyid(@Path("clas") String clas, @Path("section") String section, @Header("db_key") String db_key);
*/
    @GET("section/byName/{clas}/{section}")
    Call<SectionByName>sectionbyid(@Path("clas") String clas,@Path("section")String section,@Header("db_key") String db_key);


    //
    @GET("teacher/behaviour_note/{id}")
    Call<BehaviourListResponce> listbehaviour(@Path("id") String id,@Header("db_key") String db_key);

    @POST("teacher/student/behaviour_note/edit")
    @FormUrlEncoded
    Call<EditBehaviour>editbehaviour(@Field("date") String date, @Field("id") String id,
                                     @Field("student_id") String student_id,
                                     @Field("teacher_id") String teacher_id,
                                     @Field("title") String title,
                                     @Field("details") String details,@Header("db_key") String db_key);
    @GET("student/byId/{id}")
    Call<GetStudentById> getstudentbyid(@Path("id") String id,@Header("db_key") String db_key);

    @GET("timetable/class/section/{clas}/{section}")
    Call<GetStudentTimetable> gettimetable(@Path("clas") String clas,@Path("section") String section,@Header("db_key") String db_key);

    @GET("library/book/issue/student/{id}")
    Call<GetBookIssued> getbookedissue(@Path("id") String id,@Header("db_key") String db_key);


    @GET("homework/class/section/{clas}/{section}")
    Call<GetHomeWork> gethomework(@Path("clas") String clas, @Path("section") String section,@Header("db_key") String db_key);

    @POST("attendance/student/leave/{id}")
    Call<StudentleaveApply>applystudentleave(@Path("id") String id, @Body ApplyLeaveStudent applyLeaveStudent,@Header("db_key") String db_key);

    @POST("attendance/student/leave/docs/upload")
    @Multipart
    Call<LeaveImage>leaveimage(@Part MultipartBody.Part file,@Header("db_key") String db_key);

    @POST("attendance/student/cancel/leave/{id}")
    Call<Leavecancel>cancelleave(@Path("id") String id,@Body Dates dates,@Header("db_key") String db_key);

    @GET("class/all")
    Call<GetAllClasses> getallclass(@Header("db_key") String db_key);

    @GET("exam/")
    Call<GetExamNames> getexamnames(@Header("db_key") String db_key);

    @POST("exam/result/workbook/upload/{clas}/{section}/{}/{}")
    @Multipart
    Call<LeaveImage>uploadresults(@Path("class") String clas,@Path("section") String section,@Path("exam") String exam,@Path("subject") String subject,@Part MultipartBody.Part file);

    @GET("seat_vacancy/")
    Call<SeatVacancyResponce> getseatvacancies(@Header("db_key") String db_key);

    @GET("event/all")
    Call<GetAllEvents> getallevents(@Header("db_key") String db_key);

    @GET("event/upcoming")
    Call<GetUpcommingEvents> getupcommingevents(@Header("db_key") String db_key);

    @POST("student_application/")
    Call<ApplicationForm>studentapplication(@Body ApplyVecancy applyVecancy,@Header("db_key") String db_key);

    @GET("school")
    Call<Homebanner>callbanner(@Header("db_key") String db_key);

    @POST("event/register/{id}")
    @FormUrlEncoded
    Call<EventRegistration>eventregistration(@Path("id") String id,@Field("student_id") String student_id,
                                             @Field("student_name") String student_name,
                                             @Field("class") String cls,
                                             @Field("section") String section,@Header("db_key") String db_key);


    @GET("school/all")
    Call<SchoolList>schoollist();

    @GET("notification/student/{id}")
    Call<Notification>notification(@Path("id") String id,@Header("db_key") String db_key);

    @GET("exam/")
    Call<GetAllExamlist>allexam(@Header("db_key") String db_key);


    @GET("stationary/category/all?")
    Call<StationaryCategory>stonarycate(@Query("type") String type, @Header("db_key") String db_key);
//allexam

    @POST("stationary/product/all")
    @FormUrlEncoded
    Call<StonaryProduct> stonaryproduct(@Field("name") String name,
                                        @Field("main_category") String main_category,
                                        @Field("sub_category") String sub_category,
                                        @Field("type") String type, @Header("db_key") String db_key);

    @POST("stationary/cart/add")
    @FormUrlEncoded
    Call<AddToCart> addtocart(@Field("person_id") String person_id,
                              @Field("person_type") String person_type,
                              @Field("product_id") String product_id,
                              @Field("size") String size,
                              @Field("color") String color,
                              @Field("quantity") String quantity,
                              @Field("product_price") String product_price,
                              @Field("total_product_price") String total_product_price, @Header("db_key") String db_key);

    @GET("stationary/cart/by/user/{id}")
    Call<GetCartByUser>Getcartbyuser(@Path("id") String id,@Header("db_key") String db_key);

    @POST("stationary/cart/remove/{id}")
    Call<RemoveProductFromCart> RemoveCart(@Path("id") String id, @Header("db_key") String db_key);

    @POST("stationary/order/create")
    Call<CreateOrder> createorder(@Body AllCartItem allCartItem, @Header("db_key") String db_key);

    @POST("stationary/payment")
    Call<PostPaymentOnline> payOnline(@Body CreateOnlinePayment createOnlinePayment,@Header("db_key") String db_key);

    @GET("stationary/order/history/{id}")
    Call<OrderHistory>orderhistory(@Path("id") String user_id,@Header("db_key") String db_key);

    @GET("stationary/orders")
    Call<GetAllOrders>getallorders(@Header("db_key") String db_key);

    @POST("stationary/order/confirm")
    Call<ConfirmStatonaryOrder>confirmorder(@Body OrdersAr ordersAr, @Header("db_key") String db_key);

    @GET("stationary/orders?")
    Call<StaonaryOrderHistory>staonaryorderhistory(@Query("status") String status, @Header("db_key") String db_key);


    @GET("non_teaching/staff/byId/{id}")
    Call<NonTeachingStaffById>getnonteachingstaffbyid(@Path("id") String user_id, @Header("db_key") String db_key);


    @GET("bus/all")
    Call<GetAllBus> getallbus(@Header("db_key") String db_key);

    @POST("bus/update/stop")
    @FormUrlEncoded
    Call<GetAllBus> updateBus(@Field("bus_number") String bus_number,
                                              @Field("stop_number") String stop_number,
                                              @Header("db_key") String db_key);

    @GET("bus/reset/stops/{id}")
    Call<GetAllBus> resetBus( @Path("id") String bus_id, @Header("db_key") String db_key);

    @POST("user/guest")
    @FormUrlEncoded
    Call<Guestregistration> guestregistration(@Field("first_name") String person_id,
                                              @Field("last_name") String person_type,
                                              @Field("phone") String product_id,
                                              @Field("email") String size,
                                              @Field("password") String color,
                                              @Header("db_key") String db_key);

    @POST("student_application/all")
    Call<GetAllApplication> getallapplication(@Body CallApplication callApplication,
                                              @Header("db_key") String db_key);

    @POST("user/firebase_id")
    @FormUrlEncoded
    Call<AddFirebaseID> addfirebase(@Field("user_type") String user_type,
                                    @Field("id") String id,
                                    @Field("firebase_id") String firebase_id,
                                    @Header("db_key") String db_key);
    @POST("non_teaching/current_location/{id}")
    @FormUrlEncoded
    Observable<UpdateCurrentLocation> updateCurrentLocation(@Path("id") String id, CurrentLocation currentLocation, @Header("db_key") String db_key);

    @POST("non_teaching/current_location/")
    @Multipart
    Observable<UserLocation> getUserLocation(@Path("id") String id,@Header("db_key") String db_key);

    @GET("parent/{id}")
    Call<GetParentsByID>getparentsbyid(@Path("id") String id, @Header("db_key") String db_key);

    @GET("notice/all")
    Call<NoticeData> getNoticeData(@Header("db_key") String db_key);

    @POST("user/guest_to_parent/{id}")
    Call<GuestParent> convertGuestToUser(@Path("id") String id,
                                        @Header("db_key") String db_key);

    @POST("accounts/fees/student")
    @FormUrlEncoded
    Call<StudentFeeOnline> studFee(@Field("student_id") String student_id,
                                   @Field("class") String cls,
                                   @Field("section") String section,
                                   @Header("db_key") String db_key);

    @POST("accounts/fees/student/{id}/{yr}")
    Call<StudentFeeS> studentFess(@Path("id") String id, @Path("yr") String yr, @Header("db_key") String db_key);

    @POST("bus/notification/student")
    @FormUrlEncoded
    Call<PickupNotification> pickupnotification(@Field("status") String status,
                                                @Field("student_id") String student_id,
                                                @Header("db_key") String db_key);

}
