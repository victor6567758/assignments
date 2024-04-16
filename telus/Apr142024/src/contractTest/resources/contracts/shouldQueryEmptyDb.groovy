package contracts

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description "should query empty db"
            request {
                method GET()
                urlPath $(c('/consumer/todos'), p('/todos'))
                headers {
                    contentType(applicationJson())
                }
            }
            response {
                headers {
                    contentType(applicationJson())
                }
                status OK()
                body ( [] )
            }
        },

]