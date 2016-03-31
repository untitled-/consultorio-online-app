'use strict';

describe('Controller Tests', function() {

    describe('Symptom Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSymptom, MockConsultation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSymptom = jasmine.createSpy('MockSymptom');
            MockConsultation = jasmine.createSpy('MockConsultation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Symptom': MockSymptom,
                'Consultation': MockConsultation
            };
            createController = function() {
                $injector.get('$controller')("SymptomDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineAppApp:symptomUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
