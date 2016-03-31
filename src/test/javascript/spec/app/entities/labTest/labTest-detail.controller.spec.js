'use strict';

describe('Controller Tests', function() {

    describe('LabTest Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLabTest, MockConsultation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLabTest = jasmine.createSpy('MockLabTest');
            MockConsultation = jasmine.createSpy('MockConsultation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'LabTest': MockLabTest,
                'Consultation': MockConsultation
            };
            createController = function() {
                $injector.get('$controller')("LabTestDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineAppApp:labTestUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
