/*
* Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package br.com.zup.beagle.automatedTests.cucumber.robots

import android.text.InputType
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withInputType
import androidx.test.espresso.matcher.ViewMatchers.withText
import br.com.zup.beagle.android.utils.toAndroidId
import br.com.zup.beagle.automatedTests.R
import br.com.zup.beagle.automatedTests.utils.WaitHelper
import br.com.zup.beagle.automatedTests.utils.action.OrientationChangeAction
import br.com.zup.beagle.automatedTests.utils.action.SmoothScrollAction
import br.com.zup.beagle.automatedTests.utils.matcher.MatcherExtension
import br.com.zup.beagle.widget.core.TextAlignment
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher

class ScreenRobot {

    fun checkViewContainsText(text: String?, waitForText: Boolean = false): ScreenRobot {
        if (waitForText) {
            WaitHelper.waitForWithElement(onView(withText(text)))
        }

        onView(allOf(withText(text))).check(matches(isDisplayed()))
        return this
    }

    fun checkViewTextColor(text: String?, color: String, waitForText: Boolean = false): ScreenRobot {
        if (waitForText) {
            WaitHelper.waitForWithElement(onView(withText(text)))
        }

        onView(allOf(withText(text), MatcherExtension.withTextColor(color))).check(matches(isDisplayed()))
        return this
    }

    fun checkViewTextAlignment(text: String?, textAlignment: TextAlignment, waitForText: Boolean = false): ScreenRobot {
        if (waitForText) {
            WaitHelper.waitForWithElement(onView(withText(text)))
        }

        onView(allOf(withText(text), MatcherExtension.withTextAlignment(textAlignment))).check(matches(isDisplayed()))
        return this
    }

    fun checkViewDoesNotContainsText(text: String?, waitForText: Boolean = false): ScreenRobot {
        if (waitForText) {
            WaitHelper.waitForWithElement(onView(withText(text)))
        }

        onView(allOf(withText(text))).check(doesNotExist())
        return this
    }

    fun checkViewIsNotDisplayed(text: String?): ScreenRobot {
        onView(allOf(withText(text))).check(matches(not(isDisplayed())))
        return this
    }

    fun typeText(hint: String, text: String): ScreenRobot {
        onView(withHint(hint)).perform(ViewActions.typeText((text)))
        return this
    }

    fun checkViewContainsHint(hint: String?, waitForText: Boolean = false): ScreenRobot {
        if (waitForText) {
            WaitHelper.waitForWithElement(onView(withHint(hint)))
        }

        onView(allOf(withHint(hint))).check(matches(isDisplayed()))
        return this
    }

    fun clickOnText(text: String?): ScreenRobot {
        onView(allOf(withText(text), isDisplayed())).perform(ViewActions.click())
        return this
    }

    fun clickOnInputWithHint(hint: String?): ScreenRobot {
        onView(allOf(withHint(hint), isDisplayed())).perform(ViewActions.click())
        return this
    }

    fun disabledFieldHint(text: String): ScreenRobot {
        onView(withHint(text)).check(matches(not(isEnabled())))
        return this
    }

    fun disabledFieldText(text: String): ScreenRobot {
        onView(withText(text)).check(matches(not(isEnabled())))
        return this
    }

    fun hintInSecondPlan(text: String): ScreenRobot {
        onView(withHint(text)).perform(pressBack())
        onView(allOf(withHint(text), isDisplayed()))
        return this
    }

    fun checkInputTypeNumber(text: String): ScreenRobot {
        onView(withHint(text)).check(matches(allOf(withInputType(InputType.TYPE_CLASS_NUMBER))))
        return this
    }

    fun typeIntoTextField(position1: Int, position2: Int, text: String?): ScreenRobot {
        onView(childAtPosition(childAtPosition(withClassName(
            Matchers.`is`("br.com.zup.beagle.android.view.custom.BeagleFlexView")), position1), position2)).perform(scrollTo(), ViewActions.replaceText(text))
        Espresso.closeSoftKeyboard()
        return this
    }

    fun scrollViewDown(): ScreenRobot {
        onView(withId(R.id.root_layout)).perform(ViewActions.swipeUp())
        return this
    }

    fun swipeLeftOnView(): ScreenRobot {
        onView(allOf(withId(R.id.root_layout))).perform(ViewActions.swipeLeft())
        return this
    }

    fun swipeRightOnView(): ScreenRobot {
        onView(withId(R.id.root_layout)).perform(ViewActions.swipeRight())
        return this
    }

    fun scrollTo(text: String?): ScreenRobot {
        onView(withText(text)).perform(scrollTo()).check(matches(isDisplayed()))
        return this
    }

    fun scrollToWithHint(text: String?): ScreenRobot {
        onView(withHint(text)).perform(scrollTo()).check(matches(isDisplayed()))
        return this
    }

    fun clickOnTouchableImage(): ScreenRobot {
        onView(childAtPosition(childAtPosition(withClassName(Matchers.`is`("br.com.zup.beagle.android.view.custom.BeagleFlexView")), 1), 1)).perform(ViewActions.click())
        return this
    }

    @Throws(InterruptedException::class)
    fun sleep(seconds: Int): ScreenRobot {
        Thread.sleep(seconds * 1000L)
        return this
    }

    fun hideKeyboard() {
        Espresso.closeSoftKeyboard()
    }

    fun scrollListToPosition(listId: String, position: Int): ScreenRobot {
        var isScrolling = true
        onView(withId(listId.toAndroidId())).perform(SmoothScrollAction(position) {
            isScrolling = false
        })
        while (isScrolling) {
            //TODO: refatorar
            Thread.sleep(0)
        }
        return this
    }

    fun clickOnListPosition(listId: String, position: Int): ScreenRobot {
        onView(withId(listId.toAndroidId()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()));
        return this
    }

    fun checkListViewItemContainsText(listId: String, position: Int, expectedText: String): ScreenRobot {
        onView(withId(listId.toAndroidId()))
            .check { view, _ ->
                view.post {
                    matches(atPosition(position, hasDescendant(withText(expectedText))))
                }
            }
        return this
    }

    fun setScreenPortrait() {
        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait())
    }

    fun setScreenLandScape() {
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape())
    }

    fun clickOnViewWithId(id: String): ScreenRobot {
        onView(withId(id.toAndroidId())).perform(click())
        return this
    }

    companion object {
        private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("Child at position $position in parent ")
                    parentMatcher.describeTo(description)
                }

                public override fun matchesSafely(view: View): Boolean {
                    val parent = view.parent
                    return (parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position))
                }
            }
        }

        fun atPosition(position: Int, @NonNull itemMatcher: Matcher<View?>): Matcher<View?>? {
            return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description) {
                    description.appendText("has item at position $position: ")
                    itemMatcher.describeTo(description)
                }

                override fun matchesSafely(view: RecyclerView): Boolean {
                    val viewHolder = view.findViewHolderForAdapterPosition(position)
                        ?: // has no item on such position
                        return false
                    return itemMatcher.matches(viewHolder.itemView)
                }
            }
        }
    }
}
