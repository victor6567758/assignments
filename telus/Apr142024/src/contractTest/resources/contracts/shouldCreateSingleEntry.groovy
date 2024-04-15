package contracts

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description "create a new todos record"
            request {
                method POST()
                urlPath $(c('/consumer/todos'), p('/todos'))
                headers {
                    contentType(applicationJson())
                }
                body '''
                {
                    "description": "foo",
                    "completion": "COMPLETED"
                }
                '''
            }
            response {
                headers {
                    contentType(applicationJson())
                }
                status OK()
                body '''
                {
                    "id": $(c(156), p(positiveInt())),
                    "description": "foo",
                    "completion": "COMPLETED"
                }
                '''
            }
        },

]