package com.example.myapplication;

import org.junit.Test;

import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class SignUpPageJUnitTest {
    private SignUpPage signUpPage = new SignUpPage();

    @Test
    public void testEmailCheck(){
        assertTrue(signUpPage.emailCheck("wardiyan1@yahoo.com"));
        assertFalse(signUpPage.emailCheck("badEmail"));
        assertFalse(signUpPage.emailCheck(null));
    }

    @Test
    public void testPasswordCheck(){
        assertTrue(signUpPage.passwordCheck("GoodP@ssW0rd"));
        assertFalse(signUpPage.passwordCheck("Short"));
        assertFalse(signUpPage.passwordCheck("NoSpecialCharacter"));
        assertFalse(signUpPage.passwordCheck("nocaps"));
        assertFalse(signUpPage.passwordCheck("NoNumbers"));
        assertFalse(signUpPage.passwordCheck(null));
    }

    @Test
    public void testSamePassword(){
        assertTrue(signUpPage.passwordSame("Samep@ssw0rd", "Samep@ssw0rd"));
        assertFalse(signUpPage.passwordSame("Samep@ssw0rd", "Notp@ssw0rd"));
    }

    @Test
    public void testFirstNameCheck(){
        assertTrue(signUpPage.firstNameCheck("FirstName"));
        assertTrue(signUpPage.firstNameCheck("First Name"));
        assertFalse(signUpPage.firstNameCheck(null));
    }

    @Test
    public void testLastNameCheck(){
        assertTrue(signUpPage.lastNameCheck("LastName"));
        assertTrue(signUpPage.lastNameCheck("Last Name"));
        assertFalse(signUpPage.lastNameCheck(null));
    }

    @Test
    public void testPhoneNumber(){
        assertTrue(signUpPage.phoneNumberCheck("5869996436"));
        assertTrue(signUpPage.phoneNumberCheck("+1 586-999-6436"));
        assertTrue(signUpPage.phoneNumberCheck("(586) 999-6436"));
        assertTrue(signUpPage.phoneNumberCheck("+1 586 999 6436"));
        assertTrue(signUpPage.phoneNumberCheck("586-999-6436"));

        // Not enough Numbers
        assertFalse(signUpPage.phoneNumberCheck("8323"));
        // Cant be characters
        assertFalse(signUpPage.phoneNumberCheck("jciods(*&(#@"));
        // Middle number has to have 3 numbers
        assertFalse(signUpPage.phoneNumberCheck("586-99-6436"));
        // Last number has to have 4 numbers
        assertFalse(signUpPage.phoneNumberCheck("586-999-636"));
        // Empty and null values
        assertFalse(signUpPage.phoneNumberCheck(""));
        assertFalse(signUpPage.phoneNumberCheck(null));
    }
}
