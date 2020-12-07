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

import Foundation

/// Markup to define an action to be triggered in response to some event
public protocol RawAction: Decodable {
    var analytics: ActionAnalyticsConfig? { get }
}

public struct ActionAnalyticsConfig: Codable, AutoInitiable {
    public let enable: Bool?
    public let attributes: [String]?
    public let additionalEntries: [String: DynamicObject]?

// sourcery:inline:auto:ActionAnalyticsConfig.Init
    public init(
        enable: Bool? = nil,
        attributes: [String]? = nil,
        additionalEntries: [String: DynamicObject]? = nil
    ) {
        self.enable = enable
        self.attributes = attributes
        self.additionalEntries = additionalEntries
    }
// sourcery:end
}

/// Defines a representation of an unknown Action
public struct UnknownAction: RawAction {
    public let type: String
    
    public var analytics: ActionAnalyticsConfig? { return nil }
    
    public init(type: String) {
        self.type = type
    }
}
