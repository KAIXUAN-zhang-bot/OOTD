package com.ootd.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ootd.app.domain.model.Color
import com.ootd.app.domain.model.Gender
import com.ootd.app.domain.model.Style
import com.ootd.app.ui.viewmodel.UserViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserScreen(viewModel: UserViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var isEditing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("我的资料")

        if (uiState.user == null) {
            UserCreationForm(viewModel)
        } else {
            if (isEditing) {
                UserEditForm(
                    user = uiState.user,
                    viewModel = viewModel,
                    onCancel = { isEditing = false }
                )
            } else {
                UserProfile(
                    user = uiState.user,
                    onEdit = { isEditing = true }
                )
            }
        }

        if (uiState.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (uiState.error != null) {
            Text("Error: ${uiState.error}")
        }
    }
}

@Composable
fun UserCreationForm(viewModel: UserViewModel) {
    var phone by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(Gender.FEMALE) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("手机号") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("昵称") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("性别")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Gender.entries.forEach { gender ->
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedGender == gender,
                        onClick = { selectedGender = gender }
                    )
                    Text(gender.name)
                }
            }
        }

        Button(
            onClick = {
                if (phone.isNotBlank() && nickname.isNotBlank()) {
                    viewModel.createUser(phone, nickname, selectedGender)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("创建账户")
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserProfile(
    user: com.ootd.app.domain.model.User,
    onEdit: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("昵称: ${user.nickname}")
        Text("性别: ${user.gender.name}")
        Text("手机: ${user.phone}")
        if (user.height != null) Text("身高: ${user.height}cm")
        if (user.weight != null) Text("体重: ${user.weight}kg")

        if (user.stylePreferences.isNotEmpty()) {
            Text("风格偏好:")
            FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                user.stylePreferences.forEach {
                    FilterChip(selected = false, onClick = {}, label = { Text(it.name) })
                }
            }
        }

        if (user.colorPreferences.isNotEmpty()) {
            Text("颜色偏好:")
            FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                user.colorPreferences.forEach {
                    FilterChip(selected = false, onClick = {}, label = { Text(it.displayName) })
                }
            }
        }

        Button(onClick = onEdit, modifier = Modifier.fillMaxWidth()) {
            Text("编辑")
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserEditForm(
    user: com.ootd.app.domain.model.User,
    viewModel: UserViewModel,
    onCancel: () -> Unit
) {
    var nickname by remember { mutableStateOf(user.nickname) }
    var height by remember { mutableStateOf(user.height?.toString() ?: "") }
    var weight by remember { mutableStateOf(user.weight?.toString() ?: "") }
    var selectedStyles by remember { mutableStateOf(user.stylePreferences) }
    var selectedColors by remember { mutableStateOf(user.colorPreferences) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("昵称") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("身高(cm)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("体重(kg)") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("风格偏好")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Style.entries.forEach { style ->
                FilterChip(
                    selected = selectedStyles.contains(style),
                    onClick = {
                        selectedStyles = if (selectedStyles.contains(style)) {
                            selectedStyles.filterNot { it == style }
                        } else {
                            selectedStyles + style
                        }
                    },
                    label = { Text(style.name) }
                )
            }
        }

        Text("颜色偏好")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Color.entries.forEach { color ->
                FilterChip(
                    selected = selectedColors.contains(color),
                    onClick = {
                        selectedColors = if (selectedColors.contains(color)) {
                            selectedColors.filterNot { it == color }
                        } else {
                            selectedColors + color
                        }
                    },
                    label = { Text(color.displayName) }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = onCancel, modifier = Modifier.weight(1f)) {
                Text("取消")
            }
            Button(
                onClick = {
                    viewModel.updateUser(
                        nickname = nickname,
                        height = height.toIntOrNull(),
                        weight = weight.toIntOrNull(),
                        stylePreferences = selectedStyles,
                        colorPreferences = selectedColors
                    )
                    onCancel()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("保存")
            }
        }
    }
}
