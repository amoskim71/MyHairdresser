package pl.patryk.myhairdresser.data.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import pl.patryk.myhairdresser.data.model.Appointment
import pl.patryk.myhairdresser.data.model.AppointmentDate
import pl.patryk.myhairdresser.data.model.Photo
import pl.patryk.myhairdresser.data.model.User


class FirebaseDatabaseHelper {

    /* ***************************************************
    *                   Firebase Database                *
    **************************************************** */
    val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    /* ***************************************************
    *                   Database users                   *
    **************************************************** */
    val usersReference: DatabaseReference by lazy {
        database.getReference("users")
    }

    fun getUserReference(uid: String): DatabaseReference {
        return usersReference.child(uid)
    }

    fun getPermissionReference(uid: String): DatabaseReference {
        return usersReference.child(uid).child("admin")
    }

    fun insertUser(uid: String, user: User) = usersReference.child(uid).setValue(user)

    fun insertPhoto(uid: String, photo: Photo) = usersReference.child(uid).child("photo").setValue(photo)

    fun updateUser(uid: String, user: User) = usersReference.child(uid).updateChildren(user.toMap())

    /* ***************************************************
    *                   Database appointments            *
    **************************************************** */
    val appointmentsReference: DatabaseReference by lazy {
        database.getReference("appointments")
    }

    fun getUserAppointmentsReference(uid: String): DatabaseReference {
        return appointmentsReference.child(uid)
    }

    fun getSpecificAppointment(uid: String, appointment_id: String): DatabaseReference {
        return appointmentsReference.child(uid).child(appointment_id)
    }

    fun updateAppointment(uid: String, appointment: Appointment) = appointmentsReference.child(uid).child(appointment.appointmentID).updateChildren(appointment.toMap())

    fun deleteAppointment(uid: String, appointment: Appointment) = appointmentsReference.child(uid).child(appointment.appointmentID).removeValue()

    /* ***************************************************
    *                   Database tokens                  *
    **************************************************** */
    val tokensReference: DatabaseReference by lazy {
        database.getReference("tokens")
    }

    fun insertToken(uid: String, token: String) = tokensReference.child(uid).child("deviceToken").setValue(token)

    /* ***************************************************
    *     Database available appointment dates           *
    **************************************************** */
    val appointmentDates: DatabaseReference by lazy {
        database.getReference("availableDates")
    }

    fun getAvailableHoursFromDayReference(date: String): DatabaseReference {
        return appointmentDates.child(date)
    }

    fun insertAppointmentDate(date: String, appointmentDate: AppointmentDate) = appointmentDates.child(date).push().setValue(appointmentDate)

    fun bookADate(date: String, key: String, appointmentDate: AppointmentDate) = appointmentDates.child(date).child(key).updateChildren(appointmentDate.toMap())

    fun cancelBooking(date: String, key: String, appointmentDate: AppointmentDate) = appointmentDates.child(date).child(key).updateChildren(appointmentDate.toMap())
}